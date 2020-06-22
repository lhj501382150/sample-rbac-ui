package netty.marshalling;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import netty.marshalling.utils.SerializableFactoryMarshalling;

public class ServerSerial {
	private EventLoopGroup boss;
	private EventLoopGroup worker;
	private ServerBootstrap bootstrap;
	public ServerSerial() {
		init();
	};
	
	private void init(){
		boss = new NioEventLoopGroup();
		worker = new NioEventLoopGroup();
		bootstrap = new ServerBootstrap();
		bootstrap.group(boss,worker);
		bootstrap.channel(NioServerSocketChannel.class);
		bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
		bootstrap.childOption(ChannelOption.SO_SNDBUF, 16*1024)
				.option(ChannelOption.SO_RCVBUF, 16*1024)
				.childOption(ChannelOption.SO_KEEPALIVE, true);
		
		
	}
	
	
	public ChannelFuture handler(int port,ChannelHandler...channelHandlers) throws InterruptedException{
		bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				 ch.pipeline().addLast(SerializableFactoryMarshalling.buildMarshallingDecoder());
				 ch.pipeline().addLast(SerializableFactoryMarshalling.buildMarshallingEncoder());
				ch.pipeline().addLast(channelHandlers);
			}
		});
		
		ChannelFuture f = bootstrap.bind(port).sync();
		return f;
	}
	
	public void release(){
		if(boss!=null){
			boss.shutdownGracefully();
		}
		if(worker!=null){
			worker.shutdownGracefully();
		}
	}
	
	public static void main(String[] args) {
		ServerSerial server = null;
		ChannelFuture f = null;
		try {
			server = new ServerSerial();
			f = server.handler(9999, new ServerSerialHandler());
			System.out.println("Server start...");
			f.channel().closeFuture().sync();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			if(f!=null){
				try {
					f.channel().closeFuture().sync();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			server.release();
		}
		
	}
}

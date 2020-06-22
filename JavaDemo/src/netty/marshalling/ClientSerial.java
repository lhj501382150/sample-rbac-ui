package netty.marshalling;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import netty.marshalling.utils.RequestMessage;
import netty.marshalling.utils.SerializableFactoryMarshalling;

public class ClientSerial {
	private EventLoopGroup worker;
	private Bootstrap bootstrap;
	public ClientSerial() {
		init();
	};
	
	private void init(){
		worker = new NioEventLoopGroup();
		bootstrap = new Bootstrap();
		bootstrap.group(worker);
		bootstrap.channel(NioSocketChannel.class);
		/*bootstrap.option(ChannelOption.SO_SNDBUF, 16*1024)
				.option(ChannelOption.SO_RCVBUF, 16*1024)
				.option(ChannelOption.SO_KEEPALIVE, true);
		*/
		
	}
	
	
	public ChannelFuture handler(String host,int port,ChannelHandler...channelHandlers) throws InterruptedException{
		bootstrap.handler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline().addLast(SerializableFactoryMarshalling.buildMarshallingDecoder());
				ch.pipeline().addLast(SerializableFactoryMarshalling.buildMarshallingEncoder());
				ch.pipeline().addLast(channelHandlers);
			}
		});
		
		ChannelFuture f = bootstrap.connect(host,port).sync();
		return f;
	}
	
	public void release(){
		if(worker!=null){
			worker.shutdownGracefully();
		}
	}
	
	public static void main(String[] args) {
		ClientSerial client = null;
		ChannelFuture f = null;
		try {
			client = new ClientSerial();
			f = client.handler("localhost",9999, new ClientSerialHandler());
//			f.channel().closeFuture().sync();
			String test = "test attahcment";
			
			byte[] arr = test.getBytes();
			RequestMessage msg = new RequestMessage(new Random().nextLong(), "test", new byte[0]);
			f.channel().writeAndFlush(msg);
			
			TimeUnit.SECONDS.sleep(1);
			f.addListener(ChannelFutureListener.CLOSE);
			
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
			client.release();
			
		}
		
	}
}

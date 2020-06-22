package netty.discard;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class DisCardServer {
	private int port;

	public DisCardServer(int port) {
		this.port = port;
	}
	
	public void run() throws Exception {
		 EventLoopGroup bossGroup = new NioEventLoopGroup();
		 EventLoopGroup workerGroup = new NioEventLoopGroup();
		 
		 try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
			.channel(NioServerSocketChannel.class)
			.childHandler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new DisCardHandler());
				}
				
			})
			.option(ChannelOption.SO_BACKLOG, 128)
			.childOption(ChannelOption.SO_KEEPALIVE, true);
			
//			绑定端口 接入连接
			ChannelFuture f = b.bind(port).sync();
			
			f.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}

	}
	
	public static void main(String[] args) throws Exception {
		  new DisCardServer(9999).run();
	}
	
}

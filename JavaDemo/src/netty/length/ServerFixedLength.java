package netty.length;

import java.nio.charset.Charset;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class ServerFixedLength {
	
	private int port;

	public ServerFixedLength(int port) {
		this.port = port;
	}

	public void run()throws Exception{
		 EventLoopGroup bossGroup = new NioEventLoopGroup();
		 EventLoopGroup workerGroup = new NioEventLoopGroup();
		 ChannelFuture f = null;
		 try {
			 ServerBootstrap b = new ServerBootstrap();
			 b.group(bossGroup,workerGroup);
			 b.channel(NioServerSocketChannel.class);
			 b.option(ChannelOption.SO_BACKLOG, 128);
			 b.childOption(ChannelOption.SO_KEEPALIVE, true);
			 b.childHandler(new ChannelInitializer<SocketChannel>() {
				 protected void initChannel(SocketChannel ch) throws Exception {
					 ChannelHandler[] handlers = new ChannelHandler[3];
					 handlers[0] = new FixedLengthFrameDecoder(3);
					 handlers[1] = new StringDecoder(Charset.forName("UTF-8"));
					 handlers[2] = new ServerHandler();
					 
					 ch.pipeline().addLast(handlers);
				 };
			});
			 
			 f = b.bind(port).sync();
			 System.out.println("Server start...");
			 
			 f.channel().closeFuture().sync();
		} finally {
			 bossGroup.shutdownGracefully();
			 workerGroup.shutdownGracefully();
			 if(f!=null){
				 f.channel().closeFuture().sync();
			 }
		}
	}



	public static void main(String[] args) throws Exception {
		new ServerFixedLength(9999).run();
	}
}

package netty.length;

import java.nio.charset.Charset;
import java.util.Scanner;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class ClientFixLength {
	
	private String host;
	private int port;
	private EventLoopGroup group;

	public ClientFixLength(String host, int port) {
		this.host = host;
		this.port = port;
	}
	
	public ChannelFuture server()throws Exception{
		Bootstrap b = new Bootstrap();
		group = new NioEventLoopGroup();
		b.group(group);
		b.channel(NioSocketChannel.class);
		b.option(ChannelOption.SO_KEEPALIVE, true);
		b.handler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				 
				ChannelHandler[] handlers = new ChannelHandler[3];
				 handlers[0] = new FixedLengthFrameDecoder(2);
				 handlers[1] = new StringDecoder(Charset.forName("UTF-8"));
				 handlers[2] = new ClientHandler();
				 ch.pipeline().addLast(handlers);
			}
		});
		ChannelFuture f = b.connect(host, port).sync();
		return f;
	}
	
	private void release(){
		if(group != null){
			this.group.shutdownGracefully();
		}
	}
	
	public static void main(String[] args) throws Exception {
		
		 ClientFixLength client = null;
		 ChannelFuture f = null;
		 try {
			 client = new ClientFixLength("192.168.170.128", 9999);
			 f = client.server();
			 Scanner sc = new Scanner(System.in);
			 while(true){
				 System.out.print("Send message to Server:");
				 String msg = sc.nextLine();
				 if(msg.equals("edit")){
					 break;
				 }
				 f.channel().writeAndFlush(Unpooled.copiedBuffer(msg.getBytes("UTF-8")));
			 }
		} finally {
			if(f!=null){
				f.channel().closeFuture().sync();
			}
			client.release();
			
		}
				 
		
	}
	
}

package websocket.basic;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.ImmediateEventExecutor;

public class ChatServer {
	
	private final ChannelGroup channelGroup = new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);
	
	private final EventLoopGroup group  = new NioEventLoopGroup();
	
	private Channel channel;
	
	public ChannelFuture start(InetSocketAddress address){
		ServerBootstrap b = new ServerBootstrap();
		b.group(group)
		 .channel(NioServerSocketChannel.class)
		 .childHandler(createInitializer(channelGroup));
		
		ChannelFuture future = b.bind(address);
		future.syncUninterruptibly();
		channel = future.channel();
		return future;
	}
	
	public ChannelInitializer<Channel> createInitializer(ChannelGroup group){
		return new ChatServerInitializer(group);
	}
	
	public void destory(){
		if(channel != null){
			channel.close();
		}
		channelGroup.close();
		group.shutdownGracefully();
	}
	
	public static void main(String[] args)throws Exception {
		int port = 9999;
		final ChatServer server = new ChatServer();
		ChannelFuture future = server.start(new InetSocketAddress(port));
		
		Runtime.getRuntime().addShutdownHook(new Thread(){
			@Override
			public void run() {
				 server.destory();
			}
		});
		
		future.channel().closeFuture().syncUninterruptibly();
	}
}

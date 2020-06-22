package netty.time;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class TimeClient {
	private String host;
	private int port;
	
	public TimeClient(String host, int port) {
		this.host = host;
		this.port = port;
	}
	
	public void run()throws Exception{
		EventLoopGroup worker = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(worker);
			b.channel(NioSocketChannel.class);
			b.option(ChannelOption.SO_KEEPALIVE, true);
			b.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					 ch.pipeline()
					 .addLast(new TimeClientDecode(),new TimeClientHandler());
				}
				 
			});
			ChannelFuture f = b.connect(host, port).sync();
			f.channel().closeFuture().sync();
			
		} finally {
			 worker.shutdownGracefully();
		}
	}
	
	public static void main(String[] args) throws Exception {
		new TimeClient("192.168.170.128", 9999).run();
	}
	
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	
	
}

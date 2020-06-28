package udp;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

public class LogEventMonitor {
	private final EventLoopGroup group;
	private final  Bootstrap bootsrap;
	public LogEventMonitor(InetSocketAddress address) {
		group = new NioEventLoopGroup();
		bootsrap = new Bootstrap();
		bootsrap.group(group)
				.option(ChannelOption.SO_BROADCAST, true);
		bootsrap.channel(NioDatagramChannel.class)
				.handler(new ChannelInitializer<Channel>() {
					@Override
					protected void initChannel(Channel ch) throws Exception {
						ch.pipeline().addLast(new LogEventDecode(),new LogEventHandler());
					}
				})
				.localAddress(address);
	}
	
	public Channel bind() throws InterruptedException{
		return bootsrap.bind(9999).syncUninterruptibly().channel();
	}
	
	public void stop() {
		group.shutdownGracefully();
	}
	
	public static void main(String[] args) {
		int port = 0;
		LogEventMonitor monitor = new LogEventMonitor(new InetSocketAddress(port));
		try {
			Channel channel = monitor.bind();
			System.out.println("LogMonitor is running...");
			channel.closeFuture().sync();
		} catch (Exception e) {
			 e.printStackTrace();
		}finally {
			monitor.stop();
		}
	}
}

package udp;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

public class LogEventBroadcaster {
	private final EventLoopGroup group;
	private final Bootstrap bootstrap;
	private final File file;
	public LogEventBroadcaster(InetSocketAddress address, File file) {
		group = new NioEventLoopGroup();
		bootstrap = new Bootstrap();
		bootstrap.group(group);
		bootstrap.channel(NioDatagramChannel.class)
				.option(ChannelOption.SO_BROADCAST, true)
				.handler(new LogEventEcode(address));
		this.file = file;
	}
	
	
	public void run() throws Exception {
		Channel ch = bootstrap.bind(0).sync().channel();
		long point = 0;
		for(;;){
			long len = file.length();
			System.out.println(point + "文件大小为：："+len);
			if(len < point){
				point = len;
			}else if(len > point){
				RandomAccessFile raf = new RandomAccessFile(file, "r");
				raf.seek(point);
				String line;
				while((line = raf.readLine()) != null){
					System.out.println(line);
					ch.writeAndFlush(new LogEvent(file.getAbsolutePath(), line));
				}
				point = raf.getFilePointer();
				
				raf.close();
			}
			
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
				break;
			}
		}
	}
	
	public void stop(){
		group.shutdownGracefully();
	}
	
	public static void main(String[] args) throws Exception {
		int port = 9999;
		String filePath = "F:/logs/log.txt";
		File f = new File(filePath);
		LogEventBroadcaster broadcaster = new LogEventBroadcaster(
											new InetSocketAddress("255.255.255.255",port)
											,f);
		try {
			broadcaster.run();
		} finally {
			broadcaster.stop();
		}
		
		
	}
	
}

package websocket.ssl;

import java.net.InetSocketAddress;
import java.security.cert.CertificateException;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import websocket.basic.ChatServer;

public class SecureChatServer extends ChatServer {
	private SslContext sslContext;
	
	public SecureChatServer(SslContext sslContext) {
		this.sslContext = sslContext;
	}

	@Override
	public ChannelInitializer<Channel> createInitializer(ChannelGroup group) {
		return new SecureChatServerInitializer(group,sslContext);
	}
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws Exception{
		int port = 9999;
		SelfSignedCertificate cert = new SelfSignedCertificate();
		SslContext context = SslContext.newServerContext(cert.certificate(), cert.privateKey());
		
		final SecureChatServer server = new SecureChatServer(context);
		
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

package websocket.ssl;

import javax.net.ssl.SSLEngine;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;
import websocket.basic.ChatServerInitializer;

public class SecureChatServerInitializer extends ChatServerInitializer {

	private final SslContext sslContext;
	
	public SecureChatServerInitializer(ChannelGroup group, SslContext sslContext) {
		super(group);
		this.sslContext = sslContext;
	}
	@Override
	protected void initChannel(Channel ch) throws Exception {
		super.initChannel(ch);
		SSLEngine engine = sslContext.newEngine(ch.alloc());
		
		engine.setUseClientMode(false);
		
		ch.pipeline().addFirst(new SslHandler(engine));
	}
}

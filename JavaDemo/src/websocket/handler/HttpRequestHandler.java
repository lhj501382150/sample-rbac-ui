package websocket.handler;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.URL;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultFileRegion;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedNioFile;

public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

	private final String wsUri;
	
	private static File INDEX;
	
	static{
		URL location =	HttpRequestHandler.class
				.getProtectionDomain()
				.getCodeSource()
				.getLocation();
		try {
			
			String path = location.toURI() + "index.html";
			System.out.println(path);
			path = path.contains("file:")?path.substring(5):path;
			INDEX = new File(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public HttpRequestHandler(String wsUri) {
		this.wsUri = wsUri;
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
	 
		if(wsUri.equalsIgnoreCase(request.getUri())){
			ctx.fireChannelRead(request.retain());
		}else{
			if(HttpHeaders.is100ContinueExpected(request)){
				send100Content(ctx);
			}
			
			RandomAccessFile file = new RandomAccessFile(INDEX, "r");
			HttpResponse response = new DefaultHttpResponse(request.getProtocolVersion(), HttpResponseStatus.OK);
			response.headers()
					.set(HttpHeaders.Names.CONTENT_TYPE,"text/html;charset=UTF-8");
			boolean keepAlive = HttpHeaders.isKeepAlive(request);
			if(keepAlive){
				response.headers()
						.set(HttpHeaders.Names.CONTENT_LENGTH,file.length());
				response.headers()
						.set(HttpHeaders.Names.CONNECTION,HttpHeaders.Values.KEEP_ALIVE);
			}
			
			ctx.write(response);
			if(ctx.pipeline().get(SslHandler.class) == null){
				ctx.write(new DefaultFileRegion(file.getChannel(), 0, file.length()));
			}else{
				ctx.write(new ChunkedNioFile(file.getChannel()));
			}
			
			ChannelFuture future = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
			if(!keepAlive){
				future.addListener(ChannelFutureListener.CLOSE);
			}
		}
	}
	
	private void send100Content(ChannelHandlerContext ctx){
		HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE);
		ctx.writeAndFlush(response);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		 cause.printStackTrace();
		 ctx.close();
	}

}

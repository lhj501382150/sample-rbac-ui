package netty.time;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TimeHandler extends ChannelInboundHandlerAdapter {
	@Override
	public void channelActive(final ChannelHandlerContext ctx) throws Exception {
		 final ByteBuf time = ctx.alloc().buffer(4);
		 
		 time.writeInt((int)(System.currentTimeMillis()/1000L+2208988800L));
		 
		 final ChannelFuture f = ctx.writeAndFlush(time);
		 
		 f.addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture cf) throws Exception {
				 assert f == cf;
				 ctx.close();
				
			}
		});
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		 cause.printStackTrace();
		 ctx.close();
	}
}

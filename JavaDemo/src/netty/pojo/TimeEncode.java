package netty.pojo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

public class TimeEncode extends ChannelOutboundHandlerAdapter {
	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		 UnixTime m = (UnixTime)msg;
		 ByteBuf encode = ctx.alloc().buffer(4);
		 encode.writeInt((int)m.value());
		 ctx.write(encode, promise);
		 
	}
}

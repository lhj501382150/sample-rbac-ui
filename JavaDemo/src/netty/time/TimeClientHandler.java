package netty.time;

import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TimeClientHandler extends ChannelInboundHandlerAdapter {
	
	private ByteBuf buf;
	
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		 buf = ctx.alloc().buffer(4);
	}
	
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		 buf.release();
		 buf = null;
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf byteBuf =  (ByteBuf)msg;
		buf.writeBytes(byteBuf);
		byteBuf.release();
		if(buf.readableBytes()>=4){
			
		}
		long currTime = (buf.readUnsignedInt() - 2208988800L) * 1000l;
		System.out.println(new Date(currTime));
		ctx.close();
		/*try {
			long currTime = (byteBuf.readUnsignedInt() - 2208988800L) * 1000l;
			System.out.println(new Date(currTime));
			ctx.close();
		} finally {
			 byteBuf.release();
		}*/
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		 cause.printStackTrace();
		 
		 ctx.close();
	}

	public ByteBuf getBuf() {
		return buf;
	}

	public void setBuf(ByteBuf buf) {
		this.buf = buf;
	}

}

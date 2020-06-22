package netty.length;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerHandler extends ChannelInboundHandlerAdapter {
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//		 ByteBuf buf = (ByteBuf)msg;
		String message =msg.toString();
		
		System.out.println("From Client:"+message);
		
		String line = "ok\r\n";
		
		ctx.writeAndFlush(Unpooled.copiedBuffer(line.getBytes("UTF-8")));
		 
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}

package netty.discard;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
/**
 * 无返回服务
 * @author PC-2020
 *
 */
public class DisCardHandler extends ChannelInboundHandlerAdapter {
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//		丢弃收到数据
//		 ((ByteBuf)msg).release();
		 ByteBuf in = (ByteBuf)msg;
		 try {
			/* while(in.isReadable()){
				 System.out.print((char)in.readByte());
				 System.out.flush();
			 }*/
			 System.out.print(in.toString(CharsetUtil.US_ASCII));
		} finally {
			ReferenceCountUtil.release(msg);
		}
		 
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//		打印异常信息
		 cause.printStackTrace();
//		 关闭连接
		 ctx.close();
	}
}

package netty.discard;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
/**
 * �޷��ط���
 * @author PC-2020
 *
 */
public class DisCardHandler extends ChannelInboundHandlerAdapter {
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//		�����յ�����
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
//		��ӡ�쳣��Ϣ
		 cause.printStackTrace();
//		 �ر�����
		 ctx.close();
	}
}

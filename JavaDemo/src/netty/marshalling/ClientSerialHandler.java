package netty.marshalling;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty.marshalling.utils.RequestMessage;
import netty.marshalling.utils.ResponseMessage;
/**
 * ���л� 
 * @author PC-2020
 *
 */
public class ClientSerialHandler extends ChannelInboundHandlerAdapter {
	/**
	 * ҵ�����߼�
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		 System.out.println("From Server:ClassName->"+msg.getClass().getName());
		 System.out.println("Msg->"+msg.toString());
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		 cause.printStackTrace();
		 ctx.close();
	}
}

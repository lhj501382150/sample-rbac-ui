package netty.marshalling;


import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty.marshalling.utils.RequestMessage;
import netty.marshalling.utils.ResponseMessage;
/**
 * 序列化 
 * @author PC-2020
 *
 */
@Sharable
public class ServerSerialHandler extends ChannelInboundHandlerAdapter {
	/**
	 * 业务处理逻辑
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		 System.out.println("From Client:ClassName->"+msg.getClass().getName());
		 System.out.println("Msg->"+msg.toString());
		 
		 if(msg instanceof RequestMessage){
			 RequestMessage request = (RequestMessage)msg;
//			 byte[] arr = request.getAttache();
//			 GzipUtils.unZip();
//			 System.out.println(new String(arr));
		 }
		 
		 ResponseMessage response = new ResponseMessage(0L,"test respone");
		 ctx.writeAndFlush(response);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		 cause.printStackTrace();
		 ctx.close();
	}
}

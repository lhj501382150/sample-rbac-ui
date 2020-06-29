package udp;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.CharsetUtil;

public class LogEventDecode extends MessageToMessageDecoder<DatagramPacket> {
	
	 
	protected void decode(ChannelHandlerContext ctx, DatagramPacket datagramPacket, List<Object> out) throws Exception {
		String fileName = "";
		String msg = "";
		
		ByteBuf buf = datagramPacket.content();
		int length = buf.readableBytes();
		int idx = buf.indexOf(0,length , LogEvent.SEPARAOR);
		fileName = buf.slice(0, idx).toString(CharsetUtil.UTF_8);
		 msg = buf.slice(idx+1,length-idx-1).toString(CharsetUtil.UTF_8);
		
		
		/*String content = buf.toString(CharsetUtil.UTF_8);
		String split = String.valueOf((char)LogEvent.SEPARAOR);
		if(content.contains(split)){
			String[] para = content.split(split);
			if(para.length==2){
				fileName = para[0];
				msg = para[1];
			}
		}*/
		LogEvent logEvent = new LogEvent(datagramPacket.sender(), fileName, msg, System.currentTimeMillis());
		out.add(logEvent);
	}

}

package udp;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.CharsetUtil;

public class LogEventEcode extends MessageToMessageEncoder<LogEvent> {
	
	private InetSocketAddress source;
	

	public LogEventEcode(InetSocketAddress source) {
		this.source = source;
	}


	@Override
	protected void encode(ChannelHandlerContext ctx, LogEvent event, List<Object> out) throws Exception {
		 byte[] file = event.getLogFile().getBytes(CharsetUtil.UTF_8);
		 byte[] msg = event.getMsg().getBytes(CharsetUtil.UTF_8);
		
		 ByteBuf buf = ctx.alloc().buffer(file.length + msg.length + 1);
		 buf.writeBytes(file);
		 buf.writeByte(LogEvent.SEPARAOR);
		 buf.writeBytes(msg);
		 
		 System.out.println("Server----"+buf.readableBytes());
		 
		 out.add(new DatagramPacket(buf, source));
		 
		 
	}

	
}

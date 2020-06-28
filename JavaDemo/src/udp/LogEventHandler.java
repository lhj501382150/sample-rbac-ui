package udp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.internal.ReflectionUtil;

public class LogEventHandler extends SimpleChannelInboundHandler<LogEvent> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, LogEvent event) throws Exception {
		 StringBuffer buff = new StringBuffer();
		 buff.append(event.getReceived());
		 buff.append("[");
		 buff.append(event.getSource());
		 buff.append("]->");
		 buff.append("[");
		 buff.append(event.getLogFile());
		 buff.append("]->");
		 buff.append(event.getMsg());
		 System.out.println(buff.toString());
		 ReferenceCountUtil.release(event);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		 cause.printStackTrace();
		 ctx.close();
	}

}

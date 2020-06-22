package netty.pojo;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class TimeDecode extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext arg0, ByteBuf in, List<Object> out) throws Exception {
		if(in.readableBytes()<4){
			return;
		}
		out.add(new UnixTime(in.readUnsignedInt()));
	}

}

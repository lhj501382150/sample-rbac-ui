package netty.marshalling.utils;

import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;

import io.netty.handler.codec.marshalling.DefaultMarshallerProvider;
import io.netty.handler.codec.marshalling.DefaultUnmarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallingDecoder;
import io.netty.handler.codec.marshalling.MarshallingEncoder;

public class SerializableFactoryMarshalling {
	
	/**
	 * ½âÂëÆ÷
	 * @return
	 */
	public static MarshallingDecoder buildMarshallingDecoder(){
		final MarshallerFactory factory = Marshalling.getProvidedMarshallerFactory("serial");
		final MarshallingConfiguration config = new MarshallingConfiguration();
		config.setVersion(5);
		
		DefaultUnmarshallerProvider provider = new DefaultUnmarshallerProvider(factory, config);
		
		MarshallingDecoder decoder = new MarshallingDecoder(provider,1024*1024*1);
		return decoder;
	}
	/**
	 * ±àÂëÆ÷
	 * @return
	 */
	public static MarshallingEncoder buildMarshallingEncoder(){
		final MarshallerFactory factory = Marshalling.getProvidedMarshallerFactory("serial");
		final MarshallingConfiguration config = new MarshallingConfiguration();
		config.setVersion(5);
		
		MarshallerProvider provider = new DefaultMarshallerProvider(factory, config);
		
		MarshallingEncoder encoder = new MarshallingEncoder(provider);
		return encoder;
	}
}

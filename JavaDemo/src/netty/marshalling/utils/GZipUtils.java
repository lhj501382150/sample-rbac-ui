package netty.marshalling.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
/**
 * Ω‚—πÀı∏®÷˙¿‡
 * @author PC-2020
 *
 */
public class GZipUtils {

	/*
	 * Ω‚—πÀı
	 */
	@SuppressWarnings("resource")
	public static byte[] unZip(byte[] source)throws Exception{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = new ByteArrayInputStream(source);
		GZIPInputStream zipIn = new GZIPInputStream(in);
		byte[] temp = new byte[256];
		int length = 0;
		while((length = zipIn.read(temp, 0, temp.length)) !=-1){
			out.write(temp, 0, length);
		}
		byte[] target = out.toByteArray();
		zipIn.close();
		out.close();
		return target;
	}
	
	public static byte[] zip(byte[] source) throws Exception{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GZIPOutputStream zipOut = new GZIPOutputStream(out);
		zipOut.write(source);
		
		zipOut.finish();
		
		byte[] target = out.toByteArray();
		zipOut.close();
		
		return target;
		
		
	}
}

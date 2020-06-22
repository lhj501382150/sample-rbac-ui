package socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientSocket {
	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket socket = new Socket("192.168.170.128", 9999);
		OutputStream os = socket.getOutputStream();
		os.write(new String("aaa").getBytes("UTF-8"));
		os.flush();
		InputStream is = socket.getInputStream();
//		is.available();
		/*byte[] arr = new byte[2];
		is.read(arr);
		System.out.println("-------"+new String(arr,"UTF-8"));*/
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line = null; 
		while ( (line = br.readLine()) !=null) {
			 
			System.out.println("-------" + line);
			
		}
		
		
	}
}

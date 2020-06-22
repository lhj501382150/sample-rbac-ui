package netty.marshalling.utils;

import java.io.Serializable;

public class RequestMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long id;
	
	private String message;
	
	private byte[] attache;
	
	public RequestMessage() {
		 super();
	}

	public RequestMessage(long id, String message, byte[] attache) {
		super();
		this.id = id;
		this.message = message;
		this.attache = attache;
	}

	@Override
	public String toString() {
		return "RequestMessage [id=" + id + ", message=" + message + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public byte[] getAttache() {
		return attache;
	}

	public void setAttache(byte[] attache) {
		this.attache = attache;
	}
	
	

}

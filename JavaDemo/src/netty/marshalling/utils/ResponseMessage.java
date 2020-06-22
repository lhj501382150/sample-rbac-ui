package netty.marshalling.utils;

import java.io.Serializable;

public class ResponseMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long id;
	
	private String message;
	
	public ResponseMessage() {
		
	}
	

	public ResponseMessage(long id, String message) {
		super();
		this.id = id;
		this.message = message;
	}

	@Override
	public String toString() {
		return "ResponseMessage [id=" + id + ", message=" + message + "]";
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
	
	

}

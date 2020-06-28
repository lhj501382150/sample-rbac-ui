package udp;

import java.net.InetSocketAddress;

public class LogEvent {
	
	public static final byte SEPARAOR = (byte)'#';
	
//	发送logEvent 源
	private InetSocketAddress source;
//	日志文件名称
	private String logFile;
//	消息内容
	private String msg;
//	接收logEvent时间
	private long received;

	/*
	 * 传入消息构造函数
	 */
	public LogEvent(String logFile, String msg) {
		this(null,logFile,msg,-1);
	}
	/*
	 * 传出消息构造函数
	 */
	public LogEvent(InetSocketAddress source, String logFile, String msg, long received) {
		this.source = source;
		this.logFile = logFile;
		this.msg = msg;
		this.received = received;
	}

	public InetSocketAddress getSource() {
		return source;
	}

	public String getLogFile() {
		return logFile;
	}

	public String getMsg() {
		return msg;
	}

	public long getReceived() {
		return received;
	}
}

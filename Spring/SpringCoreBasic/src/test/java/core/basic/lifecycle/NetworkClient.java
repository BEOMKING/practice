package core.basic.lifecycle;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import lombok.Data;

@Data
public class NetworkClient {

	private String url;
	
	public NetworkClient() {
		System.out.println("생성자 " + url);	
	}
	
	public void connect() {
		System.out.println("connect " + url);
	}
	
	public void call(String message) {
		System.out.println("call " + url + " message " + message);
	}
	
	public void disconnect() {
		System.out.println("close " + url);
	}

	@PostConstruct
	public void init() throws Exception {
		System.out.println("init");
		connect();
		call("초기화 연결 메시지");
	}

	@PreDestroy
	public void close() throws Exception {
		System.out.println("close");
		disconnect();
	}
	
}

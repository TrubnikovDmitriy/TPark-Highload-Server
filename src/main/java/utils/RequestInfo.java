package utils;

import java.util.HashMap;


public class RequestInfo {

	private final String method;
	private final String uri;
	private final String version;
	private final HashMap<String, String> headers;

	public RequestInfo(String method, String uri, String version) {
		this.method = method;
		this.uri = uri;
		this.version = version;
		this.headers = new HashMap<String, String>();
	}


	public String getHeader(String key) {
		return headers.get(key);
	}

	public String putHeader(String key, String value) {
		return headers.put(key, value);
	}

	public String putHeader(String[] header) {
		return header.length == 2 ?
				headers.put(header[0], header[1]) : null;
	}

	public String getMethod() {
		return method;
	}

	public String getURI() {
		return uri;
	}

	public String getVersion() {
		return version;
	}
}

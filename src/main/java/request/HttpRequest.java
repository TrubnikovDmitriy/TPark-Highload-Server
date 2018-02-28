package request;

import utils.RequestInfo;

public class HttpRequest {

	private final RequestInfo ri;

	public HttpRequest(String data) {

		// Отделяем первую строчку от заголовков
		final String[] httpRequest = data.split("\n", 2);
		this.ri = parseRequestLine(data.split("\n", 2)[0]);

		if (httpRequest.length > 1) {
			// Парсим заголовки в хеш-мапу
			for (String headerField : httpRequest[1].split("\n")) {
				ri.putHeader(headerField.split(": ", 2));
			}
		}
	}

	private RequestInfo parseRequestLine(String requestLine) {

		// examle: "GET /hello/world HTTP/1.1"
		final String[] mainInfo = requestLine.split(" ", 3);

		if (mainInfo.length < 3) {
			throw new RuntimeException("Invalid HTTP request line: " + requestLine);
		}
		return new RequestInfo(
				mainInfo[0], // method
				mainInfo[1], // URI
				mainInfo[2]  // version
		);
	}


	public RequestInfo getRequestInfo() {
		return ri;
	}
}

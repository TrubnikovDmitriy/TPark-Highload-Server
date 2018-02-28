package request;

import utils.RequestInfo;

public class HttpRequest {

	private final RequestInfo ri;

	public HttpRequest(String data) {

		// Отделяем первую строчку от заголовков
		this.ri = parseRequestLine(data.split("\n", 2)[0]);
		final String headerFields = data.split("\n", 2)[1];

		// Парсим заголовки в хеш-мапу
		for (String headerField : headerFields.split("\n")) {
			ri.putHeader(headerField.split(": ", 2));
		}
	}

	private RequestInfo parseRequestLine(String requestLine) {

		// examle: "GET /hello/world HTTP/1.1"
		final String[] mainInfo = requestLine.split(" ", 3);

		return new RequestInfo(
				mainInfo[0], // method
				mainInfo[1], // URI
				mainInfo[2]  // version
		);
	}
}

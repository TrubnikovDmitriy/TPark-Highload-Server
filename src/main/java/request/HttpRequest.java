package request;

public class HttpRequest {

	private final RequestData rd;

	public HttpRequest(String data) {

		// Отделяем первую строчку от заголовков
		final String[] httpRequest = data.split("\n", 2);
		this.rd = parseRequestLine(data.split("\n", 2)[0]);

		if (httpRequest.length > 1) {
			// Парсим заголовки в хеш-мапу
			for (String headerField : httpRequest[1].split("\n")) {
				rd.putHeader(headerField.split(": ", 2));
			}
		}
	}

	private RequestData parseRequestLine(String requestLine) {

		// examle: "GET /hello/world HTTP/1.1"
		final String[] mainInfo = requestLine.split(" ", 3);

		if (mainInfo.length < 3) {
			throw new RuntimeException("Invalid HTTP request line: " + requestLine);
		}
		return new RequestData(
				mainInfo[0], // method
				mainInfo[1], // URI
				mainInfo[2]  // version
		);
	}

	public RequestData getRequestData() {
		return rd;
	}
}

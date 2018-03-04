package request;

import utils.HttpUtils;


public class HttpRequest {

	private final RequestData rd;

	public HttpRequest(String data) {

		// Отделяем первую строчку от заголовков
		final String[] httpRequest = data.split(HttpUtils.CLRF, 2);
		this.rd = parseRequestLine(data.split(HttpUtils.CLRF, 2)[0]);

		if (httpRequest.length > 1) {
			// Парсим заголовки в хеш-мапу
			for (String headerField : httpRequest[1].split(HttpUtils.CLRF)) {
				rd.putHeader(headerField.split(": ", 2));
			}
		}
	}

	private RequestData parseRequestLine(String requestLine) {

		// examle: "GET /hello world?query=42 HTTP/1.1"
		final String[] mainInfo = requestLine.split(" ", 2);
		final String method = mainInfo[0];

		final Integer indexOfVersion = mainInfo[1].lastIndexOf("HTTP");
		if (indexOfVersion == -1) {
			throw new RuntimeException("Invalid HTTP request line: " + requestLine);
		}
		final String version = mainInfo[1].substring(indexOfVersion);
		final String uri = mainInfo[1]
				.substring(0, indexOfVersion)
				.split("\\?")[0]  // drop query-parameters
				.replace("/..", "")
				.trim();

		return new RequestData(method, uri, version);
	}

	public RequestData getRequestData() {
		return rd;
	}
}

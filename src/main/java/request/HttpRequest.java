package request;

import io.netty.util.CharsetUtil;
import utils.HttpUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;


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

		// examle: "GET /hello%20world?query=42 HTTP/1.1"
		final String[] mainInfo = requestLine.split(" ", 3);
		if (mainInfo.length < 3) {
			throw new RuntimeException("Invalid HTTP request line: " + requestLine);
		}

		return new RequestData(
				mainInfo[0], // method
				handleURI(mainInfo[1]), // URI
				mainInfo[2]  // version
		);
	}

	private String handleURI(String rowURI) {

		// Handle the URI: delete '/..', drop query-parameters and etc.
		final String treatedURI = rowURI
				.split("\\?")[0]
				.replace("/..", "");

		try {
			return URLDecoder.decode(treatedURI, CharsetUtil.UTF_8.toString());
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public RequestData getRequestData() {
		return rd;
	}
}

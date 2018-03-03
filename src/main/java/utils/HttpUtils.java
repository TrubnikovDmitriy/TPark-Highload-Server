package utils;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


public class HttpUtils {

	public static final int DEFAULT_PORT = 5555;
	public static final int DEFAULT_CPU = 2;
	public static final String DEFAULT_ROOT = ".";


	public static final String CLRF = "\r\n";
	public static final String HTTP_VERSION_1_1 = "HTTP/1.1";

	public static final String STATUS_OK = "200 OK";
	public static final String STATUS_NOT_FOUND = "404 Not Found";
	public static final String STATUS_NOT_ALLOWED = "405 Method Not Allowed";


	public static final String HEADER_CONNECTION = "Connetcion";
	public static final String HEADER_SERVER = "Server";
	public static final String HEADER_DATE = "Date";
	public static final String HEADER_CONTENT_LENGTH = "Content-Length";
	public static final String HEADER_CONTENT_TYPE = "Content-Type";

	public static final String SERVER_NAME = "d.trubnikov";
	public static final String CONNECTON_CLOSE = "close";
	public static String getDateRFC1123() {
		return ZonedDateTime.now().format(DateTimeFormatter.RFC_1123_DATE_TIME);
	}

	public static final String TYPE_HTML = "text/html";
	public static final String TYPE_CSS = "text/css";
	public static final String TYPE_JS = "text/javascript";
	public static final String TYPE_JPG = "image/jpeg";
	public static final String TYPE_PNG = "image/png";
	public static final String TYPE_GIF = "image/gif";
	public static final String TYPE_SWF = "application/x-shockwave-flash";
	public static final String TYPE_DEFAULT = "text/plain";

}

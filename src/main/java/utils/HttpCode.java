package utils;


//@SuppressWarnings("TODO")
public enum HttpCode {

	GET("GET"),
	HEAD("HEAD"),
	OTHER("");

	private final String method;

	HttpCode(String method) {
		this.method = method;
	}
}

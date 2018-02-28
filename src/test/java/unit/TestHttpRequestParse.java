package unit;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.Timeout;
import org.apache.http.client.methods.HttpGet;
import request.HttpRequest;
import utils.RequestInfo;


import java.util.concurrent.TimeUnit;

public class TestHttpRequestParse extends Assert {

	private HttpGet request;

	@Rule
	public final ExpectedException thrown = ExpectedException.none();

	@Rule
	public final Timeout timeout = new Timeout(1, TimeUnit.SECONDS);

	@Before
	public void setUp() {
		request = new HttpGet("/path");
		request.addHeader("key", "value");
		request.addHeader("main_question", "42");
		request.addHeader("FOO", "BAR");
	}

	@Test
	public void testRequestLine() {

		final RequestInfo ri = new HttpRequest(request.toString()).getRequestInfo();

		assertEquals(ri.getMethod(), request.getMethod());
		assertEquals(ri.getURI(), request.getURI().toString());
		assertEquals(ri.getVersion(), request.getProtocolVersion().toString());
	}

	@Test
	public void testHeaders() {

		final StringBuilder builder = new StringBuilder(request.toString() + '\n');
		for (Header header : request.getAllHeaders()) {
			builder.append(header.toString()).append('\n');
		}

		final RequestInfo ri = new HttpRequest(builder.toString()).getRequestInfo();
		for (Header header : request.getAllHeaders()) {
			final String key = header.getName();
			final String value = header.getValue();
			assertEquals(ri.getHeader(key), value);
		}
	}

	@Test(expected = RuntimeException.class)
	public void testEmptyRequest() {
		new HttpRequest("");
	}

	@Test(expected = RuntimeException.class)
	public void testNotEnougthParametres() {
		new HttpRequest("GET /without/version");
	}
}

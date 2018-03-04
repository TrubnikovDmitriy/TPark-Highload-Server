package response;

import utils.HttpUtils;

import java.io.File;


public class ResponseData {

	private final StringBuilder headers = new StringBuilder();
	private final StringBuilder status = new StringBuilder();
	private File file;

	public ResponseData(String httpVersion) {
		status.append(httpVersion).append(' ');
	}

	public void setHttpStatus(String httpStatus) {
		status.append(httpStatus);
	}

	public ResponseData setHeader(String header, String value) {
		headers.append(header).append(": ")
				.append(value).append(HttpUtils.CLRF);
		return this;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public File getFile() {
		return this.file;
	}

	@Override
	public String toString() {
		return status
				.append(HttpUtils.CLRF)
				.append(headers)
				.append(HttpUtils.CLRF)
				.toString();
	}
}

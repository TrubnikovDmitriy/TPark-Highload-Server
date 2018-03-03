package response;

import java.io.File;

public class ResponseData {

	private final StringBuilder headers = new StringBuilder();
	private final StringBuilder status = new StringBuilder();
	private static final String CLRF = "\r\n";
	private File file;

	public ResponseData(String httpVersion) {
		status.append(httpVersion).append(' ');
	}

	public void setHttpStatus(String httpStatus) {
		status.append(httpStatus);
	}

	public void setHeader(String header, String value) {
		headers.append(header).append(": ")
				.append(value).append(CLRF);
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
				.append(CLRF)
				.append(headers)
				.append(CLRF)
				.toString();
	}
}

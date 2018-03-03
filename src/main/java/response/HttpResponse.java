package response;

import config.Config;
import utils.HttpUtils;
import request.RequestData;

import java.io.File;


public class HttpResponse {

	private final ResponseData responseData;
	private final RequestData requestData;
	private final File file;

	public HttpResponse(RequestData requestData) {

		this.requestData = requestData;
		final File checkFile = new File(Config.getRoot() + requestData.getURI());
		file = checkFile.isFile() ? checkFile :
				new File(Config.getRoot() + requestData.getURI() + "/index.html");

		responseData = new ResponseData(HttpUtils.HTTP_VERSION_1_1);

		responseData.setHeader(HttpUtils.HEADER_SERVER, HttpUtils.SERVER_NAME);
		responseData.setHeader(HttpUtils.HEADER_CONNECTION, HttpUtils.CONNECTON_CLOSE);
		responseData.setHeader(HttpUtils.HEADER_DATE, HttpUtils.getDateRFC1123());


		if (!requestData.isMethodAllowed()) {
			responseData.setHttpStatus(HttpUtils.STATUS_NOT_ALLOWED);
			return;
		}

		if (!file.exists()) {
			responseData.setHttpStatus(HttpUtils.STATUS_NOT_FOUND);
			return;
		}

		responseData.setHttpStatus(HttpUtils.STATUS_OK);
		uploadFile();
	}

	private void uploadFile() {
		responseData.setHeader(HttpUtils.HEADER_CONTENT_LENGTH, String.valueOf(file.length()));
		responseData.setHeader(HttpUtils.HEADER_CONTENT_TYPE, getContentType(file.getName()));
		responseData.setFile(file);
	}

	private String getContentType(String filename) {

		final Integer indexOfExtension = filename.lastIndexOf('.');

		if (indexOfExtension <= 0) {
			return HttpUtils.TYPE_DEFAULT;
		}

		switch (filename.substring(indexOfExtension + 1)) {

			case "html": return HttpUtils.TYPE_HTML;
			case "css": return HttpUtils.TYPE_CSS;
			case "js": return HttpUtils.TYPE_JS;
			case "jpg": return HttpUtils.TYPE_JPG;
			case "jpeg": return HttpUtils.TYPE_JPG;
			case "png": return HttpUtils.TYPE_PNG;
			case "gif": return HttpUtils.TYPE_GIF;
			case "swf": return HttpUtils.TYPE_SWF;

			default: return HttpUtils.TYPE_DEFAULT;
		}
	}

	public ResponseData getResponseData() {
		return responseData;
	}
}

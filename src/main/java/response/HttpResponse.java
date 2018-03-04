package response;

import config.Config;
import utils.HttpUtils;
import request.RequestData;

import java.io.File;


public class HttpResponse {

	private final ResponseData responseData;
	private File file;

	public HttpResponse(RequestData requestData) {

		responseData = new ResponseData(HttpUtils.HTTP_VERSION_1_1)
				.setHeader(HttpUtils.HEADER_SERVER, HttpUtils.SERVER_NAME)
				.setHeader(HttpUtils.HEADER_CONNECTION, HttpUtils.CONNECTON_KEEP)
				.setHeader(HttpUtils.HEADER_DATE, HttpUtils.getDateRFC1123());


		if (!requestData.isMethodAllowed()) {
			responseData.setHttpStatus(HttpUtils.STATUS_NOT_ALLOWED);
			return;
		}

		// If directory/file does not exist, the error 404 returned
		file = new File(Config.getRoot() + requestData.getURI());
		if (!file.exists()) {
			responseData.setHttpStatus(HttpUtils.STATUS_NOT_FOUND);
			return;
		}

		if (file.isDirectory()) {
			// If the directory is requested, it is return index.html of this directory
			file = new File(Config.getRoot() + requestData.getURI() + "/index.html");

			// If index.html does not exist, the error 403 returned
			// (странное поведение, но так требуют тестовые кейсы)
			if (!file.exists()) {
				responseData.setHttpStatus(HttpUtils.STATUS_FORBIDDEN);
				return;
			}
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

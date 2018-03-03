package config;

import utils.HttpUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Config {

	private static final String CONFIG_PATH = "/etc/httpd.conf";

	private static final String FIELD_PORT = "listen";
	private static final String FIELD_CPU = "cpu_limit";
	private static final String FIELD_ROOT = "document_root";

	private static int port = HttpUtils.DEFAULT_PORT;
	private static int cpu = HttpUtils.DEFAULT_CPU;
	private static String root = HttpUtils.DEFAULT_ROOT;


	public static void parseConfig() {
		try {
			Files.lines(Paths.get(CONFIG_PATH)).forEach(Config::parseLine);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void parseLine(String line) {

		if (line.contains(FIELD_PORT)) {
			port = Integer.valueOf(line.split(" ")[1]);
		}
		if (line.contains(FIELD_CPU)) {
			cpu = Integer.valueOf(line.split(" ")[1]);
		}
		if (line.contains(FIELD_ROOT)) {
			root = line.split(" ")[1];
		}
	}

	public static int getPort() {
		return port;
	}

	public static int getCpu() {
		return cpu;
	}

	public static String getRoot() {
		return root;
	}
}

import config.Config;
import server.NettyServer;


public class Main {
	public static void main(String[] args) throws InterruptedException {
		Config.parseConfig();
		new NettyServer(Config.getPort()).run();
	}
}

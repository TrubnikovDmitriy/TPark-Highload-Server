import server.NettyServer;

import java.io.File;

public class Main {
	public static void main(String[] args) throws InterruptedException {
		final int port = 5555;
		new NettyServer(port).run();
	}
}

package server;

import config.Config;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.socket.SocketChannel;


public class NettyServer {

	private final int port;
	private static final int BACKLOG_QUEUE = 1024;
	private static final int EPOLL_WORKERS = 2;

	public NettyServer(int port) {
		this.port = port;
	}

	public void run() throws InterruptedException{

		final EventLoopGroup workers =
				new EpollEventLoopGroup(Math.min(EPOLL_WORKERS, Config.getCpu()));
		try {

			final ServerBootstrap server = new ServerBootstrap();
			server.group(workers)
					.channel(EpollServerSocketChannel.class)
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline().addLast(new NettyServerHandler());
						}
					})
					.option(ChannelOption.SO_BACKLOG, BACKLOG_QUEUE)
					.childOption(ChannelOption.SO_KEEPALIVE, true);

			final ChannelFuture f = server.bind(port).sync();
			f.channel().closeFuture().sync();

		} finally {
			workers.shutdownGracefully();
		}
	}

}

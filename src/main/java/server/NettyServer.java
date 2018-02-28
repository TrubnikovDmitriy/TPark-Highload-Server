package server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {

	private final int port;
	private final int MASTER_COUNT = 1;
	private final int WORKER_COUNT = 4;


	public NettyServer(int port) {
		this.port = port;
	}

	public void run() throws InterruptedException{

		final EventLoopGroup masterGroup = new NioEventLoopGroup(MASTER_COUNT);
		final EventLoopGroup workerGroup = new NioEventLoopGroup(WORKER_COUNT);

		try {

			final ServerBootstrap server = new ServerBootstrap();
			server.group(masterGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline().addLast(new NettyServerHandler());
						}
					})
					.option(ChannelOption.SO_BACKLOG, 128)
					.childOption(ChannelOption.SO_KEEPALIVE, true);

			final ChannelFuture f = server.bind(port).sync();
			f.channel().closeFuture().sync();

		} finally {
			masterGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

}

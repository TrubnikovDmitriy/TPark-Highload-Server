package server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import request.HttpRequest;


public class NettyServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
//// DISCARD server
//		final ByteBuf in = (ByteBuf) msg;
//		try {
//			if (in.isReadable()) {
//				System.out.println(in.toString(CharsetUtil.ISO_8859_1));
//				System.out.flush();
//			}
//		} finally {
//			in.release();
//		}

//// ECHO server
//		ctx.write(msg);
//		ctx.flush();
////		ctx.writeAndFlush(msg);
		final ByteBuf data = (ByteBuf) msg;
		// TODO Кодировка (буквы 'М','у' и др.)
		System.out.println(data.toString(CharsetUtil.ISO_8859_1));
		new HttpRequest(data.toString(CharsetUtil.ISO_8859_1));
		ctx.writeAndFlush(msg);
	}

//	@Override
//	public void channelActive(final ChannelHandlerContext ctx) {
////TIMEOUT server
//		final ByteBuf time = ctx.alloc().buffer(4);
//		time.writeInt((int) (System.currentTimeMillis()  / 1000L + 2208988800L));
//
//		final ChannelFuture f = ctx.writeAndFlush(time);
//		f.addListener(ChannelFutureListener.CLOSE);
//	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}

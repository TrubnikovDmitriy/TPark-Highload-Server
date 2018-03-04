package server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import request.HttpRequest;
import response.HttpResponse;
import request.RequestData;
import response.ResponseData;


public class NettyServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {

		final String data = ((ByteBuf) msg).toString(CharsetUtil.UTF_8);

		final RequestData request = new HttpRequest(data).getRequestData();
		final ResponseData response = new HttpResponse(request).getResponseData();

		final String payload = response.toString();

		final ByteBuf respData = ctx.alloc().buffer(payload.length());
		respData.writeBytes(payload.getBytes());

		final ChannelFuture f;
		if (response.getFile() == null || request.isMethodHead()) {
			f = ctx.writeAndFlush(respData);
		} else {
			ctx.write(respData);
			f = ctx.writeAndFlush(new DefaultFileRegion(
					response.getFile(), 0, response.getFile().length()));
		}
		f.addListener(ChannelFutureListener.CLOSE);
		ReferenceCountUtil.release(msg);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}

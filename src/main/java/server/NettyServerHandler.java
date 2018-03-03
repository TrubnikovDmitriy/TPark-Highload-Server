package server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.util.CharsetUtil;
import request.HttpRequest;
import response.HttpResponse;
import request.RequestData;
import response.ResponseData;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;


public class NettyServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws UnsupportedEncodingException {

		final ByteBuf data = (ByteBuf) msg;
		final String decodeData = URLDecoder.decode(
				data.toString(CharsetUtil.UTF_8), CharsetUtil.UTF_8.toString());

		final RequestData request = new HttpRequest(decodeData).getRequestData();
		final ResponseData response = new HttpResponse(request).getResponseData();

		final String payload = response.toString();

		final ByteBuf respData = ctx.alloc().buffer(payload.length());
		respData.writeBytes(payload.getBytes());

		final ChannelFuture f;
		if (response.getFile() == null) {
			f = ctx.writeAndFlush(respData);

		} else {
			ctx.write(respData);
			f = ctx.writeAndFlush(new DefaultFileRegion(
					response.getFile(), 0, response.getFile().length()));
		}
		f.addListener(ChannelFutureListener.CLOSE);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}

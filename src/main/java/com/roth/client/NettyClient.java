package com.roth.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

import static com.roth.api.Constants.*;

public class NettyClient {
    private static final Logger LOG = LoggerFactory.getLogger(NettyClient.class);

    public static void main(String... args) throws Exception {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workerGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new ClientHandler());
                    }
                });
        connect(bootstrap, SERVER_HOST, SERVER_PORT, CLIENT_RETREY_NUMBER).sync();
    }

    private static ChannelFuture connect(Bootstrap bootstrap, String host, int port, int retry) {
        return bootstrap.connect(host, port)
                .addListener(future -> {
                    if (future.isSuccess()) {
                        LOG.info("connect success");
                    } else if (retry == 0) {
                        LOG.error("retry number exhaustion, fail connect");
                    } else {
                        int order = (CLIENT_RETREY_NUMBER - retry) + 1;
                        int delay = 1 << order;
                        LOG.warn("connect false, {} retry connect", retry);
                        bootstrap.config().group().schedule(() ->
                                connect(bootstrap, host, port, retry), delay, TimeUnit.SECONDS);
                    }
                });
    }
}

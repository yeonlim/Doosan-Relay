package com.daeu.fts.socket.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.codec.string.StringDecoder;
import org.springframework.core.env.Environment;

public class FileMessageServerInitializer extends ChannelInitializer<SocketChannel> {

    private Environment env;

    public FileMessageServerInitializer(Environment env) {
        this.env = env;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        // EventExecutorGroup e1 = new DefaultEventExecutorGroup(16);
        // EventExecutorGroup e2 = new DefaultEventExecutorGroup(8);
        /*
        ch.pipeline().addLast(new ByteToMessageDecoder() {
            @Override
            protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
                out.add(in.readBytes(in.readableBytes())); }
        });
        */
        pipeline.addLast(new ObjectDecoder(ClassResolvers.weakCachingConcurrentResolver(null)));
        pipeline.addLast(new ObjectEncoder());
        // pipeline.addLast(new StringDecoder());
        // pipeline.addLast(e1, new ByteArrayDecoder());
        // pipeline.addLast(e1, new FileMessageDecoder());

        pipeline.addLast("handler", new FileMessageServerHandler(this.env));
    }
}

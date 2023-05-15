package com.daeu.fts.socket.client;

import com.daeu.fts.socket.common.FileMessage;
import com.daeu.fts.socket.common.ResultMsg;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * @Project       : 원격저장소 통신용 클라이언트단 socket 모듈
 * @프로그램 설명   : Netty를 이용하여 특정 서버에서 요청하는 파일을 전송하기 위한 클라이언트단 socket 모듈
 * @파일명         : SocketClient.java
 * @작성자         : 양강현
 * @작성일         : 2017. 9. 20.
 * @version       : 0.8
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일         수정자      수정내용
 *  -------------  --------    ---------------------------
 *   2017. 9. 20.  양강현      최초 생성
 *
 * </pre>
 */
public class SocketClient {

    /**
     * 로그관리자 인스턴스
     */
    private static final Logger logger = LoggerFactory.getLogger(SocketClient.class);

    static final String IP = "127.0.0.1"; // 서버 소켓 IP 지정
    static final int SOCKET_PORT = 17979; // 서버 소켓 포트 번호 지정
    static final int BUFFER_SIZE = 262144;// bufferSize 지정


    static final String FILE_NAME = "jdk-8u202-windows-x64.exe";
    static final String SRC_PATH  = "C:\\Users\\JWJANG\\Desktop\\";  // 복사 대상 파일 경로
    static final String DEST_PATH = "C:\\Users\\JWJANG\\Desktop\\JW\\Project\\삼성중공업\\file\\"; // 복사할 타겟 경로

    /**
     * main method
     * @param args
     */
    public static void main(String[] args) throws IOException {
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            FileMessage echoFile = new FileMessage(SRC_PATH + FILE_NAME, DEST_PATH + FILE_NAME);
            ResultMsg resultMsg = new ResultMsg();

            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ByteToMessageDecoder() {
                                @Override
                                protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
                                    out.add(in.readBytes(in.readableBytes())); }
                            });
                            ch.pipeline().addLast(new ObjectDecoder(ClassResolvers.weakCachingConcurrentResolver(null)));
                            ch.pipeline().addLast(new ObjectEncoder());
                            ch.pipeline().addLast(new SocketClientHandler(echoFile, resultMsg, BUFFER_SIZE));
                        }
                    });


            logger.info("connect to {}:{}", IP, SOCKET_PORT);

            ChannelFuture future = bootstrap.connect(IP, SOCKET_PORT).sync();

            // 소켓이 닫힐떄까지 대기
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        } finally {
            group.shutdownGracefully();
        }
    }
}
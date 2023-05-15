package com.daeu.fts.socket.client;

import com.daeu.fts.socket.common.FileMessage;
import com.daeu.fts.socket.common.ResultMsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class SocketClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 로그관리자 인스턴스
     */
    private static final Logger logger = LoggerFactory.getLogger(SocketClientHandler.class);

    private int bufferSize;
    private FileMessage echoFile;
    private ResultMsg resultMsg;
    private String filePath;

    public SocketClientHandler(FileMessage echoFile, ResultMsg resultMsg, int bufferSize) {
        this.echoFile = echoFile;
        this.resultMsg = resultMsg;
        this.bufferSize = bufferSize;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(this.echoFile);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        logger.info("channelRead");

        /**
         * 파일 받기
         */
        if(msg instanceof FileMessage) {
            try {
                logger.info("start trans!!!!");

                FileMessage ef = (FileMessage) msg;
                int SumCountPackage = ef.getSumCountPackage();
                int countPackage = ef.getCountPackage();
                byte[] bytes = ef.getBytes();

                logger.info("countPackage => {}", countPackage);

                // 새롭게 복사할 파일
                File file = new File(this.filePath);
                RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");

                // 전송된 파일의 용량과 dataLength를 비교
                if((bytes.length < this.bufferSize) && countPackage == 1) {
                    this.bufferSize = bytes.length;
                }

                // int의 최대수는 2147483647 이기 때문에 하단 if문 추가함.
                if (countPackage < 8193) {
                    randomAccessFile.seek(countPackage * this.bufferSize - this.bufferSize);
                } else {
                    randomAccessFile.seek((long) countPackage * this.bufferSize - this.bufferSize);
                }
                // randomAccessFile.seek(countPackage*dataLength-dataLength);
                randomAccessFile.write(bytes);
                countPackage = countPackage + 1;

                if (countPackage <= SumCountPackage) {

                    ef.setCountPackage(countPackage);
                    ctx.writeAndFlush(ef);
                    randomAccessFile.close();

                    // logger.info(String.valueOf(countPackage));
                } else {
                    randomAccessFile.close();
                    ctx.close();

                    this.resultMsg.setResultCode("SUCCESS");
                    logger.info("File copy success !");
                }
            } catch (IOException i) {
                this.resultMsg.setResultCode("FAIL");
                this.resultMsg.setDetailMsg(i.getMessage());

                ctx.close();
            }
        /**
         * 에러 발생시 메시지 받기
         */
        } else if(msg instanceof ResultMsg) {
            // 에러발생!!
            ResultMsg resultMsg = (ResultMsg) msg;

            this.resultMsg.setResultCode(resultMsg.getResultCode());
            this.resultMsg.setDetailMsg(resultMsg.getDetailMsg());

            // TODO - Error.log => 파일전송 실패 내용 기록

            ctx.close();
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("exceptionCaught", cause);
        ctx.close();
    }
}

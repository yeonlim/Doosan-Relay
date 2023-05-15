package com.daeu.fts.socket.server;

import com.daeu.fts.socket.common.FileMessage;
import com.daeu.fts.socket.common.FileMessageTimeStamp;
import com.daeu.fts.socket.common.ResultMsg;
import com.daeu.fts.util.FtpUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class FileMessageServerHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(FileMessageServerHandler.class);

    private RandomAccessFile randomAccessFile;
    private final Environment env;

    public FileMessageServerHandler(Environment env) {
        this.env = env;
    }

    /**
     * @description 채널이 접속되자마자 실행할 코드를 정의
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("channelActive");
    }

    /**
     * @description 채널을 읽을 때 동작할 코드를 정의
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("channelRead");

        // 파일 전송
        if(msg instanceof FileMessage) {
            FileMessage fileMessage = (FileMessage) msg;
            logger.info("FileMessage : {}", fileMessage);


            if(fileMessage.getFileUploadFlag()) {
                // 업로드 (SFDC -> NAS)
            } else {
                // 다운로드 (SFDC <- NAS)
                fileMessageProcess(ctx, fileMessage);
            }


        // 타임스탬프 보내기
        // 원격지에 있는 파일이 파일을 요청하는 서버의 파일보다 최신인지 확인하기 위한 로직
        } else if(msg instanceof FileMessageTimeStamp) {
            FileMessageTimeStamp fileMessageTimeStamp = (FileMessageTimeStamp) msg;
            logger.info("FileMessageTimeStamp : {}", fileMessageTimeStamp);

            fileMessageTimeStampProcess(ctx, fileMessageTimeStamp);
        }
        /*
        String str = (String)msg;
        logger.info("str : {}", str);

        ctx.writeAndFlush(str);
        */
    }

    private void fileMessageProcess(ChannelHandlerContext ctx, FileMessage fileMessage) throws Exception {
        // 첫번째 조각을 보낼 경우
        int dataLength = env.getProperty("SOCKET.BUFFER.SIZE", Integer.class);
        if(fileMessage.getCountPackage() == 0) {
            try {
                File file = new File(fileMessage.getSrcFilePath());

                // NAS 파일유무 확인
                if (FtpUtil.getInstance(env).fileExists(fileMessage.getNasFileName())) { // 파일이 존재한다면

                    logger.info("{} transfer start!!!", fileMessage.getSrcFilePath());

                    randomAccessFile = new RandomAccessFile(file, "r");
                    randomAccessFile.seek(0);

                    // 파일용량이 dataLength보다 작은지 확인..작다면 전송바이트를 실제파일용량과 같게한다.
                    if(randomAccessFile.length() < dataLength) {
                        dataLength = (int) randomAccessFile.length();
                    }

                    int sumCountpackage;
                    if ((randomAccessFile.length() % dataLength) == 0) {
                        sumCountpackage = (int) (randomAccessFile.length() / dataLength);
                    } else {
                        sumCountpackage = (int) (randomAccessFile.length() / dataLength) + 1;
                    }
                    byte[] bytes = new byte[dataLength];

                    if (randomAccessFile.read(bytes) != -1) {
                        FileMessage msgFile = new FileMessage();
                        msgFile.setSumCountPackage(sumCountpackage);
                        msgFile.setCountPackage(1);
                        msgFile.setBytes(bytes);
                        msgFile.setSrcFilePath(file.getName());
                        ctx.writeAndFlush(msgFile);
                    }

                } else { // 파일이 존재하지 않다면
                    ResultMsg resultMsg = new ResultMsg();
                    resultMsg.setResultCode("FAIL");
                    resultMsg.setDetailMsg("파일이 없습니다.");

                    ctx.writeAndFlush(resultMsg);
                }
            } catch (IOException i) {
                i.printStackTrace();
            }

            // 파일 조각을 계속 보낸다.
        } else {
            try {
                // FileMessage msgFileMessage = (FileMessage) msg;
                int countPackage = fileMessage.getCountPackage();

                // int의 최대수는 2147483647 이기 때문에 하단 if문 추가함.
                // 아래 if문이 없으면 4GB 이상의 파일을 전송할 때 오류 발생함.
                if (countPackage < 8193) {
                    randomAccessFile.seek(countPackage * dataLength - dataLength);
                } else {
                    randomAccessFile.seek((long) countPackage * dataLength - dataLength);
                }

                int byteLength;
                long remainderFileCount = randomAccessFile.length() - randomAccessFile.getFilePointer();

                if (remainderFileCount < dataLength) {
                    byteLength = (int) remainderFileCount;
                } else {
                    byteLength = dataLength;
                }

                byte[] bytes = new byte[byteLength];
                if (randomAccessFile.read(bytes) != -1 && remainderFileCount > 0) {
                    fileMessage.setCountPackage(countPackage);
                    fileMessage.setBytes(bytes);
                    ctx.writeAndFlush(fileMessage);
                } else {
                    logger.info("{} transfer End!!!", fileMessage.getSrcFilePath());

                    randomAccessFile.close();
                    ctx.close();
                }
            } catch (IOException i) {
                i.printStackTrace();
                randomAccessFile.close();
            }
        }
    }

    private void fileMessageTimeStampProcess(ChannelHandlerContext ctx, FileMessageTimeStamp fileMessageTimeStamp) throws Exception {
        File file = new File(fileMessageTimeStamp.getFileNm());

        if(file.exists()) {

            // 파일인지 체크
            if(file.isFile()) { // 파일일 경우
                fileMessageTimeStamp.setFileAt(true);
                fileMessageTimeStamp.setTimeStamp(file.lastModified());

            } else if(file.isDirectory()){ // 폴더이면
                fileMessageTimeStamp.setFileAt(false);

                // 폴더의 파일리스트 가져오기
                File[] fileList = file.listFiles();

                if(fileList != null && fileList.length > 0) {
                    List<String> list = new ArrayList<>();

                    for (File imageFile : fileList) {
                        list.add(imageFile.getName());
                    }

                    fileMessageTimeStamp.setFileList(list);

                    File firstFile = new File(fileMessageTimeStamp.getFileNm() + File.separator + fileMessageTimeStamp.getFileList().get(0));
                    fileMessageTimeStamp.setTimeStamp(firstFile.lastModified());
                }
            }

            ctx.writeAndFlush(fileMessageTimeStamp);
        } else {
            ResultMsg resultMsg = new ResultMsg();
            resultMsg.setResultCode("FAIL");
            resultMsg.setDetailMsg("파일이 없습니다.");

            ctx.writeAndFlush(resultMsg);
        }
    }

    // 예외가 발생할 때 동작할 코드를 정의 합니다.
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error(cause.getMessage());

        // 쌓여있는 트레이스를 출력
        cause.printStackTrace();

        // randomAccessFile.close();

        // 컨텍스트를 종료
        ctx.close();
    }
}

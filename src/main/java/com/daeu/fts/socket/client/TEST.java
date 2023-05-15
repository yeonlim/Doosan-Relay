package com.daeu.fts.socket.client;

import com.daeu.fts.util.FtpUtil;

public class TEST {
    static final String FILE_NAME = "jdk-8u202-windows-x64.exe";
    static final String SRC_PATH  = "C:/Users/JWJANG/Desktop/";  // 복사 대상 파일 경로
    static final String DEST_PATH = "C:/Users/JWJANG/Desktop/JW/Project/삼성중공업/file/"; // 복사할 타겟 경로
    static final String FTP_PATH = "/SDF"; // FTP

    public static void main(String[] args) throws Exception {
        FtpUtil ftp = new FtpUtil("127.0.0.1", 21, "JWJANG", "wkdwlsdn", FTP_PATH);
        System.out.println(ftp.fileExists(FILE_NAME));
    }
}

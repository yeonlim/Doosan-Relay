package com.daeu.fts.socket.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class FileMessage implements Serializable {
    private static final long serialVersionUID = 8953150675564212795L;

    /**
     * 파일 분할 번호 합계
     */
    private int sumCountPackage;
    /**
     * 파일 분할 번호
     */
    private int countPackage;
    /**
     * 파일 바이트
     */
    private byte[] bytes;
    /**
     * 활용시스템 ID
     */
    private String systemId;
    /**
     * 업로드/다운로드 여부 (true : 업로드, false : 다운로드)
     */
    private Boolean fileUploadFlag;
    /**
     * 세일즈포스 첨부 ID
     */
    private String sfdcId;
    /**
     * 원 파일명
     */
    private String originFileName;
    /**
     * NAS 전송 파일명 (YYYYMMDDHHmmss-UUID)
     */
    private String nasFileName;
    /**
     * 원본 파일경로
     */
    private String srcFilePath;
    /**
     * 복사 파일경로
     */
    private String destFilePath;

    public FileMessage() { }

    public FileMessage(String srcFilePath, String destFilePath) {
        this.srcFilePath = srcFilePath;
        this.destFilePath = destFilePath;
    }

    public FileMessage(String sfdcId, String originFileName, String nasFileName) {
        this.sfdcId         = sfdcId;
        this.originFileName = originFileName;
        this.nasFileName    = nasFileName;
    }
}

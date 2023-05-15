package com.daeu.fts.socket.common;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class FileMessageTimeStamp implements Serializable {

    private static final long serialVersionUID = -5098260754414505842L;

    /**
     * 파일명
     */
    private String fileNm;

    /**
     * 타임스탬프
     */
    private Long timeStamp;

    /**
     * 파일유무
     */
    private boolean fileAt;

    /**
     * 파일리스트
     */
    private List<String> fileList;
}

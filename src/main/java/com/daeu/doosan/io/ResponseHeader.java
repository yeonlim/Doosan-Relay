package com.daeu.doosan.io;

import lombok.Data;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Data
public class ResponseHeader {
    /**
     * 1. 공통 정상 0000
     * 2. 기타 기타 오류 9999
     */
    private String resultCode;

    /**
     * 2. interface result status
     */
    private String resultMessage;

    private List<Error> errorList;
    private String body;
}

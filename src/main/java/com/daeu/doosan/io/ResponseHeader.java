package com.daeu.doosan.io;

import lombok.Data;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Data
public class ResponseHeader {
    /**
     * 정상 0000, 오류 9999
     */
    private String resultCode;

    private String resultMessage;

    private List<Error> errorList;

    private String body;
}

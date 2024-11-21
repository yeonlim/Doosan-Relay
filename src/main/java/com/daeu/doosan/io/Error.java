package com.daeu.doosan.io;

import lombok.Data;

@Data
public class Error {
    private int recordId;
    private String errorCode;
    private String errorMessage;
}

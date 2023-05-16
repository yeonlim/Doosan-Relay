package com.daeu.suprema.io;

import lombok.Data;

@Data
public class Error {
    private int recordId;
    private String errorCode;
    private String errorMessage;
}

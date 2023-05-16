package com.daeu.suprema.io;

import lombok.Data;

import java.util.List;

@Data
public class ResponseHeader2 {
    /**
     * 1. GUID for message<br><br>
     *
     * 1. 공통 정상 0000<br>
     * 2. 공통 필수값 오류 1000<br>
     * 3. 공통 데이터 오류 1003<br>
     * 4. 공통 DB 오류 1004<br>
     * 5. 공통 비인가 요청 오류 1005<br>
     * 6 공통 조회내역 없음 1006<br>
     * 7. 기타 서비스 일시 중지중 9000<br>
     * 8. 기타 DB Error 9001<br>
     * 9. 기타 기타 오류 9999
     */
    private String resultCode;

    /**
     * 2. interface result status
     */
    private String resultMessage;

    private List<Error2> errorList;
}

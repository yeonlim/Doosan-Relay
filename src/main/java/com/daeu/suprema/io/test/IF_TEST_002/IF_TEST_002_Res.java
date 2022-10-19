package com.daeu.suprema.io.test.IF_TEST_002;

import com.daeu.suprema.io.ResponseHeader;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class IF_TEST_002_Res extends ResponseHeader {
	private List<Map<String, Object>> userList;
}

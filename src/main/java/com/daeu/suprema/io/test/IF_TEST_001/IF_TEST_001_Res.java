package com.daeu.suprema.io.test.IF_TEST_001;

import com.daeu.suprema.io.ResponseHeader;
import lombok.Data;

import java.util.List;

@Data
public class IF_TEST_001_Res extends ResponseHeader {
	private List<TestObj> objList;
}

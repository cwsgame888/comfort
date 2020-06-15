package com.pos.service;

import java.util.List;

import com.core.common.map.DataMap;

public interface PosCommonService {
	// 코드 종류 데이터 호출
	List<DataMap> selectCodeList(DataMap map) throws Exception;
	// 코드 종류 데이터 저장
	void insertCode(DataMap map) throws Exception;
	// 코드 종류 데이터 수정
	void updateCode(DataMap map) throws Exception;
	// 코드 종류 데이터 삭제
	void deleteCode(DataMap map) throws Exception;
	// 백업데이터 조회
	List<DataMap> selectBackupData(DataMap map) throws Exception;
}

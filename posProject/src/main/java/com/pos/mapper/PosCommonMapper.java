package com.pos.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.core.common.map.DataMap;

@Mapper
public interface PosCommonMapper {
	// 코드 종류 데이터 호출
	public List<DataMap> selectCodeList(DataMap map) throws Exception;
	// 코드 종류 데이터 저장
	public void insertCode(DataMap map) throws Exception;
	// 코드 종류 데이터 수정
	public void updateCode(DataMap map) throws Exception;
	// 코드 종류 데이터 삭제
	public void deleteCode(DataMap map) throws Exception;
	// 백업데이터 조회
	public List<DataMap> selectBackupData(DataMap map) throws Exception;
}
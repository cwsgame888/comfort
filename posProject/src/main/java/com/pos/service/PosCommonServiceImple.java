package com.pos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.common.map.DataMap;
import com.pos.mapper.PosCommonMapper;

@Service
public class PosCommonServiceImple implements PosCommonService{

	@Autowired
	private PosCommonMapper posCommonMapper;
	
	// 코드 종류 데이터 호출
	@Override
	public List<DataMap> selectCodeList(DataMap map) throws Exception {
		return posCommonMapper.selectCodeList(map);
	}
	// 코드 종류 데이터 저장
	@Override
	public void insertCode(DataMap map) throws Exception {
		posCommonMapper.insertCode(map);
	}
	// 코드 종류 데이터 수정
	@Override
	public void updateCode(DataMap map) throws Exception {
		posCommonMapper.updateCode(map);
	}
	// 코드 종류 데이터 삭제
	@Override
	public void deleteCode(DataMap map) throws Exception {
		posCommonMapper.deleteCode(map);
	}
	
	// 백업데이터 조회
	@Override
	public List<DataMap> selectBackupData(DataMap map) throws Exception {
		return posCommonMapper.selectBackupData(map);
	}
}
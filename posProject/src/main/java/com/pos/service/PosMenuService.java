package com.pos.service;

import java.util.List;

import com.core.common.map.DataMap;

public interface PosMenuService {
	// 메뉴 데이터 갯수 호출
	int selectMenuCount(DataMap map) throws Exception;
	// 메뉴 데이터 호출
	List<DataMap> selectMenuList(DataMap map) throws Exception;
	// 메뉴 데이터 저장
	void insertMenu(DataMap map) throws Exception;
	// 메뉴 데이터 수정
	void updateMenu(DataMap map) throws Exception;
	// 메뉴 데이터 삭제
	void deleteMenu(DataMap map) throws Exception;
}

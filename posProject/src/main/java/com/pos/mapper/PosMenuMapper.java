package com.pos.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.core.common.map.DataMap;

@Mapper
public interface PosMenuMapper {
	// 메뉴 리스트 가져오는 함수
	public List<DataMap> selectMenuList(DataMap map) throws Exception;
	// 메뉴 단건 가져오는 함수 - 필수값 메뉴 ID
	public DataMap selectMenu(DataMap map) throws Exception;
	// 메뉴 갯수 가져오기
	public int selectMenuCount(DataMap map) throws Exception;
	// 메뉴 추가
	public void insertMenu(DataMap map) throws Exception;
	// 메뉴 수정
	public void updateMenu(DataMap map) throws Exception;
	// 메뉴 우선순위 재설정 - 우선순위가 기존보다 작을때
	public void updatePlusPriorityMenu(DataMap map) throws Exception;
	// 메뉴 우선순위 재설정 - 우선순귀가 기존보다 클때
	public void updateMinusPriorityMenu(DataMap map) throws Exception;
	// 메뉴 우선순위 재설정 - 메뉴가 삭제 되었을때 
	public void deleteUpdatePriorityMenu(DataMap map) throws Exception;
	// 메뉴 삭제
	public void deleteMenu(DataMap map) throws Exception;
}
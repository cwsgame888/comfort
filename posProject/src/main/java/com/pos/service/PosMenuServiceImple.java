package com.pos.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.common.map.DataMap;
import com.pos.mapper.PosMenuMapper;

@Service
public class PosMenuServiceImple implements PosMenuService{
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private PosMenuMapper posMenuMapper;
	
	// 메뉴 데이터 갯수 호출
	@Override
	public int selectMenuCount(DataMap map) throws Exception {
		return posMenuMapper.selectMenuCount(map);
	}
	// 메뉴 데이터 호출
	@Override
	public List<DataMap> selectMenuList(DataMap map) throws Exception {
		// 메뉴 갯수
		int menuCount = selectMenuCount(map);
		map.put("total", menuCount/NumberUtils.toInt(map.get("rows")+"", 20) + 1);
		return posMenuMapper.selectMenuList(map);
	}
	// 메뉴 데이터 저장
	@Override
	public void insertMenu(DataMap map) throws Exception {
		// 중복 메뉴ID 체크
		DataMap tempMap = posMenuMapper.selectMenu(map);
		if(!StringUtils.isBlank(tempMap.getString("MENU_ID"))){
			throw new Exception("이미 사용 하고 있는 메뉴ID입니다.");
		}
		// 우선순위 최대값 체크
		tempMap.put("MENU_KIND", map.get("MENU_KIND"));
		int menuCount = selectMenuCount(tempMap);
		int inputMenuCount = map.getInt("MENU_PRIORITY", menuCount + 1);
		if(menuCount + 1 < inputMenuCount){
			throw new Exception("현재 해당 메뉴종류에서 설정할 수 있는 우선순위 최대값은 " + (menuCount + 1) + "입니다.");
		}
		map.put("MENU_PRIORITY", inputMenuCount);
		posMenuMapper.updatePlusPriorityMenu(map);
		posMenuMapper.insertMenu(map);
	}
	// 메뉴 데이터 수정
	@Override
	public void updateMenu(DataMap map) throws Exception {
		// 우선순위 최대값 체크
		int menuCount = selectMenuCount(map);
		int inputMenuCount = 0;
		boolean menuKindSame = true;
		// 기존 저장되어 있는 메뉴 정보
		DataMap tempMap = posMenuMapper.selectMenu(map);
		if(tempMap != null && tempMap.get("MENU_KIND").equals(map.get("MENU_KIND"))){
			inputMenuCount = map.getInt("MENU_PRIORITY", menuCount);
		} else {
			menuCount = menuCount + 1;
			inputMenuCount = map.getInt("MENU_PRIORITY", menuCount);
			menuKindSame = false;
		}
		if(log.isDebugEnabled()){
			log.debug("설정할 수 있는 우선순위 최대값 : " + menuCount + ", 입력 받은 우선순위 : " + inputMenuCount);
		}
		if(menuCount < inputMenuCount){
			throw new Exception("현재 해당 메뉴종류에서 설정할 수 있는 우선순위 최대값은 " + menuCount + "입니다.");
		}
		map.put("MENU_PRIORITY", inputMenuCount);
		
		// 우선순위가 변경되어 있는지 체크해서 변경되었으면 우선순위 재설정
		// 변경되는 우선순위 값이 기존 저장되어 있는 값도다 작으면 updatePlusPriorityMenu 크면 updateMinusPriorityMenu
		// 만약 다른 메뉴 종류에서 넘어온거라면 넘어오기전 메뉴종류에 들어있는 메뉴도 우선순위 재설정
		if(!menuKindSame){
			// 변경되는 메뉴 종류가 다른경우
			posMenuMapper.deleteUpdatePriorityMenu(tempMap);
			posMenuMapper.updatePlusPriorityMenu(map);
		} else if(tempMap != null && tempMap.getInt("MENU_PRIORITY") > inputMenuCount){
			// 메뉴종류는 같고 변경되는 우선순위 값이 기존 저장되어 있는 값도다 작을경우
			posMenuMapper.updatePlusPriorityMenu(map);
		} else {
			// 메뉴종류는 같고 변경되는 우선순위 값이 기존 저장되어 있는 값도다 클경우
			posMenuMapper.updateMinusPriorityMenu(map);
		}
		posMenuMapper.updateMenu(map);
	}
	// 메뉴 데이터 삭제
	@Override
	public void deleteMenu(DataMap map) throws Exception {
		posMenuMapper.deleteUpdatePriorityMenu(map);
		posMenuMapper.deleteMenu(map);
	}
}
package com.pos.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.common.map.DataMap;
import com.pos.mapper.PosOrderMapper;
import com.pos.mapper.PosTableMapper;

@Service
public class PosTableServiceImple implements PosTableService{

	@Autowired
	private PosTableMapper PosTableMapper;
	
	@Autowired
	private PosOrderMapper posOrderMapper;
	
	@Override
	public List<DataMap> selectTableList(DataMap map) throws Exception {
		return PosTableMapper.selectTableList(map);
	}

	/*
	 * 테이블 추가
	 * */
	@Override
	public void insertTable(DataMap map) throws Exception {
		PosTableMapper.insertTable(map);
	}
	
	/*
	 * 테이블 수정
	 * */
	@Override
	public void updateTable(DataMap map) throws Exception {
		PosTableMapper.updateTable(map);
	}

	/*
	 * 테이블 삭제
	 * */
	@Override
	public void deleteTable(DataMap map) throws Exception {
		PosTableMapper.deleteTable(map);
	}

	/*
	 * 현재 주문상태가 활성화 되어 있는 주문 조회
	 * */
	@Override
	public List<DataMap> selectOrderList() throws Exception {
		return PosTableMapper.selectOrderList();
	}

	/*
	 * 당일 주문 전체 정보 가져오기
	 * */
	@Override
	public DataMap selectAllOrderInfo() throws Exception {
		return PosTableMapper.selectAllOrderInfo();
	}
	
	/*
	 * 주문테이블 이동
	 * */
	@Override
	public void updateMoveOrderTable(DataMap map) throws Exception {
		DataMap tempMap = new DataMap();
		
		// 드래그 주문번호
		String moveFromOrderNum = map.getString("MOVE_FROM_ORDER_NUM");
		// 드랍 주문번호
		String moveToOrderNum = map.getString("MOVE_TO_ORDER_NUM");
		
		// 같은 주문번호가 들어오면 스킵
		if(moveFromOrderNum.equals(moveToOrderNum)) {
			
		} else if(!StringUtils.isEmpty(moveFromOrderNum) && !StringUtils.isEmpty(moveToOrderNum)){
			// 드래그, 드랍 위치에 주문번호가 둘다 존재하면 드랍쪽 위치에 있는 주문번호로 합치기
			tempMap.put("MOVE_FROM_ORDER_NUM", moveFromOrderNum.trim());
			tempMap.put("MOVE_TO_ORDER_NUM", moveToOrderNum.trim());
			// 주문메뉴목록 업데이트 후 기존 주분번호는 삭제
			PosTableMapper.updateSumOrderTable(tempMap);
			PosTableMapper.deleteSumOrderTable(tempMap);
			
			tempMap.put("ORDER_NUM", moveToOrderNum);
			// 테이블 주문 정보 업데이트
			posOrderMapper.updateOrder(tempMap);
		} else {
			// 드래그 테이블 업데이트
			tempMap.put("TABLE_ROW", map.get("MOVE_TO_ROW"));
			tempMap.put("TABLE_COL", map.get("MOVE_TO_COL"));
			tempMap.put("ORDER_NUM", moveFromOrderNum);
			PosTableMapper.updateMoveOrderTable(tempMap);
			
			// 드랍테이블 업데이트
			tempMap.put("TABLE_ROW", map.get("MOVE_FROM_ROW"));
			tempMap.put("TABLE_COL", map.get("MOVE_FROM_COL"));
			tempMap.put("ORDER_NUM", moveToOrderNum);
			PosTableMapper.updateMoveOrderTable(tempMap);
		}
	}
}
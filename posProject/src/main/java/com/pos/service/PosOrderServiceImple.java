package com.pos.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.common.map.DataMap;
import com.pos.mapper.PosOrderMapper;

@Service
public class PosOrderServiceImple implements PosOrderService{
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private PosOrderMapper posOrderMapper;
	
	// 메뉴 데이터 호출
	@Override
	public List<DataMap> selectMenuList(DataMap map) throws Exception {
		return posOrderMapper.selectMenuList(map);
	}
	
	// 주문 메뉴 넣기
	@Override
	public void insertOrderMenu(ArrayList<DataMap> orderMenuList) throws Exception {
		// 테이블 정보를 빼서 따로 맵에 셋팅 후 리스트에서는 삭제
		DataMap orderInfoMap = orderMenuList.get(0);
		String ORDER_NUM = orderInfoMap.getString("ORDER_NUM");
		// 신규 주문 여부
		boolean newOrder = false;
		if(StringUtils.isBlank(ORDER_NUM)){
			newOrder = true;
		}
		// 주문번호가 없으면 신규 주문
		if(newOrder){
			// 오늘 날짜 + 4자리 시퀀스 -> 201706090001
			String todayNextOrderNum = posOrderMapper.todayNextOrderNum();
			if("null".equals(todayNextOrderNum)){
				Date today = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				
				// 해당날짜 주문이 첫건일경우 주문번호 생성
				ORDER_NUM = sdf.format(today) + "0001";
			} else {
				ORDER_NUM = todayNextOrderNum;
			}
			orderInfoMap.put("ORDER_NUM", ORDER_NUM);
			
			// 신규주문 저장
			posOrderMapper.insertOrder(orderInfoMap);
		}
		orderMenuList.remove(0);
		
		// 신규주문, 추가주문에 대한 메뉴 없데이트
		for(DataMap orderMenu : orderMenuList){
			orderMenu.put("ORDER_NUM", ORDER_NUM);
			if(log.isDebugEnabled()){
				log.debug("#############주문 메뉴 정보##################");
				log.debug("#############주문번호##############["+orderMenu.get("ORDER_NUM")+"]");
				log.debug("#############메뉴ID##############["+orderMenu.get("MENU_ID")+"]");
				log.debug("#############메뉴명###############["+orderMenu.get("MENU_NAME")+"]");
				log.debug("#############수량################["+orderMenu.get("QUANTITY")+"]");
				log.debug("#############단가################["+orderMenu.get("ORI_PRICE")+"]");
			}
			// 수량만큼 반복문
			int quantity = orderMenu.getInt("QUANTITY", 1);
			for (int i = 0; i < quantity; i++) {
				posOrderMapper.insertOrderMenu(orderMenu);
			}
		}
		// 최종 메뉴금액 합계 및 추가주문 변동사항 적용(인원수, 주문시간)
		updateOrder(orderInfoMap);
	}
	

	// 테이블 주문 정보 호출
	@Override
	public DataMap getOrderInfo(DataMap map) throws Exception {
		return posOrderMapper.getOrderInfo(map);
	}

	// 결제 되지 않은 주문 메뉴 데이터 호출
	@Override
	public List<DataMap> getOrderMenuInfo(DataMap map) throws Exception {
		return posOrderMapper.getOrderMenuInfo(map);
	}

	// 주문메뉴 서비스(단건, 전체)
	@Override
	public void updateServiceOrderMenu(DataMap map) throws Exception {
		posOrderMapper.updateServiceOrderMenu(map);
		// 최종 메뉴금액 합계 및 추가주문 변동사항 적용(인원수, 주문시간)
		updateOrder(map);
	}
	// 주문메뉴 취소(단건, 전체)
	@Override
	public void deleteOrderMenu(DataMap map) throws Exception {
		posOrderMapper.deleteOrderMenu(map);
		// 최종 메뉴금액 합계 및 추가주문 변동사항 적용(인원수, 주문시간)
		updateOrder(map);
	}
	
	// 메뉴 정보 가져오기
	public DataMap getMenuInfo(String menuId) throws Exception{
		return posOrderMapper.getMenuInfo(menuId);
	}
	// 최종 메뉴금액 합계 및 추가주문 변동사항 적용(인원수, 주문시간)
	public void updateOrder(DataMap map) throws Exception{
		posOrderMapper.updateOrder(map);
	}

	// 주문메뉴 전광판 카운트 조회
	@Override
	public DataMap getOrderListCount() throws Exception {
		return posOrderMapper.getOrderListCount();
	}

	// 주문메뉴 전광판 조회
	@Override
	public List<DataMap> getOrderList() throws Exception {
		return posOrderMapper.getOrderList();
	}
	
	// 주문 결제
	@Override
	public void insertPayment(DataMap map) throws Exception {
		// 주문 테이블 추가
		posOrderMapper.insertPayment(map);
		// 주문 테이블 완료처리
		posOrderMapper.updatePaymentOrder(map);
		// 주문메뉴 테이블 완료처리
		updateCompleteOrder(map);
	}

	// 주문 취소 하기
	@Override
	public void updateCancelOrder(DataMap map) throws Exception {
		// 주문 테이블 취소 하기
		posOrderMapper.updateCancelOrder(map);
		// 주문메뉴 테이블 취소 하기
		posOrderMapper.updateCancelOrderMenu(map);
	}

	// 주문들어간 메뉴 조리 완료처리
	@Override
	public void updateCompleteOrder(DataMap map) throws Exception {
		posOrderMapper.updateCompleteOrder(map);
	}
}
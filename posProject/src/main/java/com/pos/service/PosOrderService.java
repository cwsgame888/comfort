package com.pos.service;

import java.util.ArrayList;
import java.util.List;

import com.core.common.map.DataMap;

public interface PosOrderService {
	// 메뉴 데이터 호출
	List<DataMap> selectMenuList(DataMap map) throws Exception;
	// 메뉴 데이터 저장
	void insertOrderMenu(ArrayList<DataMap> arrayList) throws Exception;
	// 메뉴 데이터 서비스
	void updateServiceOrderMenu(DataMap map) throws Exception;
	// 메뉴 데이터 삭제
	void deleteOrderMenu(DataMap map) throws Exception;
	// 테이블 주문 정보 호출
	DataMap getOrderInfo(DataMap map) throws Exception;
	// 결제 되지 않은 주문 메뉴 데이터 호출
	List<DataMap> getOrderMenuInfo(DataMap map) throws Exception;

	// 주문메뉴 전광판 카운트 조회
	DataMap getOrderListCount() throws Exception;

	// 주문메뉴 전광판 조회
	List<DataMap> getOrderList() throws Exception;

	// 주문메뉴 결제
	void insertPayment(DataMap map) throws Exception;
	// 주문 취소 하기
	void updateCancelOrder(DataMap map) throws Exception;
	
	// 주문들어간 메뉴 조리 완료처리
	void updateCompleteOrder(DataMap map) throws Exception;
}

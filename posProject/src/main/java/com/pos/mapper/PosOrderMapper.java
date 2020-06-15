package com.pos.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.core.common.map.DataMap;

@Mapper
public interface PosOrderMapper {
	// 메뉴 리스트 가져오는 함수
	public List<DataMap> selectMenuList(DataMap map) throws Exception;
	// 메뉴 정보 가져오기
	public DataMap getMenuInfo(String menuId) throws Exception;
	// 주문번호 가져오기
	public DataMap getOrderInfo(DataMap map) throws Exception;
	// 주문메뉴 가져오기
	public List<DataMap> getOrderMenuInfo(DataMap map) throws Exception;
	// 당일 주문 건수
	public String todayNextOrderNum() throws Exception;
	// 테이블 주문 정보 저장
	public void insertOrder(DataMap map) throws Exception;
	// 테이블 주문 정보 업데이트
	public void updateOrder(DataMap map) throws Exception;
	// 주문메뉴 추가
	public void insertOrderMenu(DataMap map) throws Exception;
	// 주문메뉴 서비스
	public void updateServiceOrderMenu(DataMap map) throws Exception;
	// 주문메뉴 취소
	public void deleteOrderMenu(DataMap map) throws Exception;
	// 주문메뉴 전광판 카운트 조회
	public DataMap getOrderListCount() throws Exception;
	// 주문메뉴 전광판 조회
	public List<DataMap> getOrderList() throws Exception;
	// 결제테이블 insert
	public void insertPayment(DataMap map) throws Exception;
	// 주문 결제(주문완료처리)
	public void updatePaymentOrder(DataMap map) throws Exception;
	// 주문 결제(주문들어간 메뉴 완료처리)
	public void updateCompleteOrder(DataMap map) throws Exception;
	// 주문 테이블 취소 하기
	public void updateCancelOrder(DataMap map) throws Exception;
	// 주문메뉴 테이블 취소 하기
	public void updateCancelOrderMenu(DataMap map) throws Exception;
}
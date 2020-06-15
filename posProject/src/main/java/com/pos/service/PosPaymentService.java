package com.pos.service;

import java.util.List;

import com.core.common.map.DataMap;

public interface PosPaymentService {
	// 결제 이력 일자 데이터 호출
	List<DataMap> selectPaymentDate(DataMap map) throws Exception;
	// 결제 이력 일자 데이터 상세 호출
	List<DataMap> selectPaymentDateDetail(DataMap map) throws Exception;

	// 달력별 결제 데이터 호출
	List<DataMap> selectSalesCalendar(DataMap map) throws Exception;
}

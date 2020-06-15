package com.pos.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.core.common.map.DataMap;

@Mapper
public interface PosPaymentMapper {
	
	// 결제 이력 일자 데이터 호출
	public List<DataMap> selectPaymentDate(DataMap map) throws Exception;
	// 결제 이력 갯수
	public int selectPaymentCount(DataMap map) throws Exception;
	// 결제 이력 일자 데이터 상세 호출
	public List<DataMap> selectPaymentDateDetial(DataMap map) throws Exception;
	// 달력별 결제 데이터 호출
	public List<DataMap> selectSalesCalendar(DataMap map) throws Exception;
}
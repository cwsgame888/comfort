package com.pos.service;

import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.common.map.DataMap;
import com.pos.mapper.PosPaymentMapper;

@Service
public class PosPaymentServiceImple implements PosPaymentService{
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private PosPaymentMapper posPaymentMapper;

	// 결제 이력 일자 데이터 호출
	@Override
	public List<DataMap> selectPaymentDate(DataMap map) throws Exception {
		return posPaymentMapper.selectPaymentDate(map);
	}

	// 결제 이력 일자 데이터 상세 호출
	@Override
	public List<DataMap> selectPaymentDateDetail(DataMap map) throws Exception {
		// 결제 이력 갯수
		int paymentCount = posPaymentMapper.selectPaymentCount(map);
		map.put("total", paymentCount/NumberUtils.toInt(map.getString("rows"), 20) + 1);
		return posPaymentMapper.selectPaymentDateDetial(map);
	}

	// 달력별 결제 데이터 호출
	@Override
	public List<DataMap> selectSalesCalendar(DataMap map) throws Exception {
		return posPaymentMapper.selectSalesCalendar(map);
	}
}
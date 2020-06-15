package com.pos.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.common.map.DataMap;
import com.pos.mapper.PosStatsMapper;

@Service
public class PosStatsServiceImple implements PosStatsService{
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private PosStatsMapper posStatsMapper;

	// 주문메뉴 중에서 메뉴 통계 데이터 가져오기
	@Override
	public List<DataMap> selectStatsOrderMenu(DataMap map) throws Exception {
		return posStatsMapper.selectStatsOrderMenu(map);
	}
	// 사용하는 전체메뉴 중에서 주문 메뉴 통계 데이터 가져오기
	@Override
	public List<DataMap> selectStatsAllOrderMenu(DataMap map) throws Exception {
		return posStatsMapper.selectStatsAllOrderMenu(map);
	}
}
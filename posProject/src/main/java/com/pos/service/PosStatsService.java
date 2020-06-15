package com.pos.service;

import java.util.List;

import com.core.common.map.DataMap;

public interface PosStatsService {
	// 주문메뉴 중에서 메뉴 통계 데이터 가져오기
	List<DataMap> selectStatsOrderMenu(DataMap map) throws Exception;

	// 사용하는 전체메뉴 중에서 주문 메뉴 통계 데이터 가져오기
	List<DataMap> selectStatsAllOrderMenu(DataMap map) throws Exception;
}

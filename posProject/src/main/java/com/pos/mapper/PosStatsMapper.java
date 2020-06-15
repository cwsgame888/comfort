package com.pos.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.core.common.map.DataMap;

@Mapper
public interface PosStatsMapper {
	
	// 주문메뉴 중에서 메뉴 통계 데이터 가져오기
	public List<DataMap> selectStatsOrderMenu(DataMap map) throws Exception;
	// 사용하는 전체메뉴 중에서 주문 메뉴 통계 데이터 가져오기
	public List<DataMap> selectStatsAllOrderMenu(DataMap map) throws Exception;
}

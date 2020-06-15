package com.pos.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.core.common.map.DataMap;

@Mapper
public interface PosTableMapper {
	
	public List<DataMap> selectTableList(DataMap map) throws Exception;
	public List<DataMap> selectOrderList() throws Exception;
	public DataMap selectAllOrderInfo() throws Exception;
	public void insertTable(DataMap map) throws Exception;
	public void updateTable(DataMap map) throws Exception;
	public void deleteTable(DataMap map) throws Exception;
	public void updateMoveOrderTable(DataMap map) throws Exception;
	public void updateSumOrderTable(DataMap map) throws Exception;
	public void deleteSumOrderTable(DataMap map) throws Exception;
}

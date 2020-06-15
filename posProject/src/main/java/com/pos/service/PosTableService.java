package com.pos.service;

import java.util.List;

import com.core.common.map.DataMap;

public interface PosTableService {
	List<DataMap> selectTableList(DataMap map) throws Exception;

	List<DataMap> selectOrderList() throws Exception;

	DataMap selectAllOrderInfo() throws Exception;
	
	void updateMoveOrderTable(DataMap map) throws Exception;

	void insertTable(DataMap map) throws Exception;
	
	void updateTable(DataMap map) throws Exception;
	
	void deleteTable(DataMap map) throws Exception;
}

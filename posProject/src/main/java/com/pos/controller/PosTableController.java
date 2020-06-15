package com.pos.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.core.common.map.DataMap;
import com.pos.service.PosTableService;


/**
 * Handles requests for the application home page.
 */
@Controller
public class PosTableController {
	Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private PosTableService posTableService;
	String excelHomeDir = "C:/Users/우리집/Desktop/table.xlsx";

	@RequestMapping(value = "/pos/posTableList")
	public ModelAndView posTableList(DataMap dataMap) throws Exception{
		ModelAndView mv = null;
		String gubun = dataMap.getString("gubun");
		if(log.isDebugEnabled()){
			log.debug("gubun::::::::" + gubun);
		}
		
		if("CHANGE".equals(gubun)){		// 테이블 수정 화면
			mv = new ModelAndView("/pos/admin/posChangeTable");
		} else {						// 테이블 사용 화면
			mv = new ModelAndView("/pos/posTableList");
			dataMap.put("tableState", "Y");
			// 현재 주문상태가 활성화 되어 있는 주문 조회
			List<DataMap> orderList = posTableService.selectOrderList();
			mv.addObject("orderList", orderList);
			
			// 당일 주문 전체 정보 가져오기
			DataMap allOrderInfo = posTableService.selectAllOrderInfo();
			mv.addObject("allOrderInfo", allOrderInfo);
		}
		List<DataMap> tableList = posTableService.selectTableList(dataMap);
		//ExcelReader reader = new ExcelReader();
		//List<Map<String, String>> list = reader.xlsxToCustomerList(excelHomeDir, 0);
		mv.addObject("tableList", tableList);
		
		int maxRow = 0;
		int maxCol = 0;
		for(int i = 0; i < tableList.size(); i++){
			if(maxRow < tableList.get(i).getInt("TABLE_ROW", 0)){
				maxRow = tableList.get(i).getInt("TABLE_ROW", 0);
			}
			if(maxCol < tableList.get(i).getInt("TABLE_COL", 0)){
				maxCol = tableList.get(i).getInt("TABLE_COL", 0);
			}
		}
		if(log.isDebugEnabled()){
			log.debug("테이블 행열 ::::::" + maxRow + " x " + maxCol);
		}
		mv.addObject("maxRow", maxRow);
		mv.addObject("maxCol", maxCol);

        return mv;
	}
	
	// 주문테이블 이동
	@RequestMapping(value = "/pos/posMoveOrderTable")
	public ModelAndView posMoveOrder(DataMap dataMap) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/pos/posTableList.do");
		posTableService.updateMoveOrderTable(dataMap);
		return mv;
	}
	
	@ResponseBody
	@RequestMapping(value = "/pos/posTableChange")
	public DataMap posTableSave(DataMap dataMap) throws Exception{
		String target = dataMap.getString("target");
		
		if("insert".equals(target)){		// 저장
			posTableService.insertTable(dataMap);
		} else if("update".equals(target)){	// 수정
			posTableService.updateTable(dataMap);
		} else if("delete".equals(target)){	// 삭제
			posTableService.deleteTable(dataMap);
		}
		
        return dataMap;
	}
	
	@RequestMapping(value = "/pos/posTime")
	public ModelAndView posTime(DataMap dataMap) throws Exception{
		ModelAndView mv = new ModelAndView("/pos/posTime");

        return mv;
	}
}
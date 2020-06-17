package com.pos.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.core.common.map.DMap;
import com.core.common.map.DataMap;
import com.pos.service.PosOrderService;
import com.pos.service.PosTableService;


/**
 * Handles requests for the application home page.
 */
@RestController
public class PosAdminController {
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private PosTableService posTableService;
	// 메뉴 서비스
	@Autowired
	private PosOrderService posOrderService;
	
	@RequestMapping(value = "/pos/posAdmin")
	public ModelAndView posTableList(@DMap DataMap dataMap) throws Exception{
		ModelAndView mv = new ModelAndView("/pos/admin/posAdminTemplate");
		List<DataMap> list = posTableService.selectTableList(dataMap);
		int maxRow = 0;
		int maxCol = 0;
		for(int i = 0; i < list.size(); i++){
			if(maxRow < list.get(i).getInt("TABLE_ROW")){
				maxRow = list.get(i).getInt("TABLE_ROW");
			}
			if(maxCol < list.get(i).getInt("TABLE_COL")){
				maxCol = list.get(i).getInt("TABLE_COL");
			}
		}
		if(log.isDebugEnabled()){
			log.debug("테이블 행열 ::::::" + maxRow + " x " + maxCol);
		}
		mv.addObject("maxRow", maxRow);
		mv.addObject("maxCol", maxCol);
		
		DataMap orderListCount = posOrderService.getOrderListCount();
		mv.addObject("orderListCount", orderListCount);
		
        return mv;
	}
}
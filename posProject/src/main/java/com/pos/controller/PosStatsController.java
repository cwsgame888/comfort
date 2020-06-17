package com.pos.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.core.common.map.DMap;
import com.core.common.map.DataMap;
import com.pos.service.PosCommonService;
import com.pos.service.PosStatsServiceImple;


/**
 * Handles requests for the application home page.
 */
@Controller
public class PosStatsController {
	Logger log = LoggerFactory.getLogger(this.getClass());
	// 결제 결과 서비스
	@Autowired
	private PosStatsServiceImple posStatsServiceImple;
	// 메뉴코드 서비스
	@Autowired
	private PosCommonService posCommonService;
	
	// 결제 이력 일자 데이터 호출
	@RequestMapping(value = "/pos/posStatsOrderMenu")
	public ModelAndView posStatsOrderMenu(@DMap DataMap dataMap) throws Exception{
		ModelAndView mv = new ModelAndView("/pos/admin/posStatsList");
		
		String target = dataMap.getString("target", "MONTH");
		
		String statsKind = dataMap.getString("STATS_KIND", "ALL");
		
		if("MONTH".equals(target)){
			// 월별
			if(StringUtils.isBlank(dataMap.getString("STATS_DATE"))){
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
				dataMap.put("STATS_DATE", sdf.format(date));
			}
		} else if("YEAR".equals(target)){
			// 년별
			if(StringUtils.isBlank(dataMap.getString("STATS_DATE"))){
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
				dataMap.put("STATS_DATE", sdf.format(date));
			}
		}
		
		// 코드 그룹 셋팅
		dataMap.put("CODE_GROUP", "MENU_KIND");
		// 메뉴 종류 가지고 옴
		List<DataMap> menuKind = posCommonService.selectCodeList(dataMap);
		
		List<DataMap> statsData;
		if("ORDER".equals(statsKind)){
			// 주문메뉴 중에서 메뉴 통계 데이터 가져오기
			statsData = posStatsServiceImple.selectStatsOrderMenu(dataMap);
		} else {
			// 사용하는 전체메뉴 중에서 주문 메뉴 통계 데이터 가져오기
			statsData = posStatsServiceImple.selectStatsAllOrderMenu(dataMap);
		}
		mv.addAllObjects(dataMap);
		mv.addObject("statsData", statsData);
		mv.addObject("menuKind", menuKind);
        return mv;
	}
}
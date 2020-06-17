package com.pos.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.core.common.map.DMap;
import com.core.common.map.DataMap;
import com.pos.service.PosCommonService;
import com.pos.service.PosMenuService;


/**
 * Handles requests for the application home page.
 */
@Controller
public class PosMenuController {
	Logger log = LoggerFactory.getLogger(this.getClass());
	// 메뉴 서비스
	@Autowired
	private PosMenuService posMenuService;
	// 메뉴 종류 서비스
	@Autowired
	private PosCommonService posCommonService;
	
	// 메뉴 설정 화면 호출
	@RequestMapping(value = "/pos/posMenuListIndex")
	public ModelAndView posMenuListIndex(@DMap DataMap dataMap) throws Exception{
		ModelAndView mv = new ModelAndView("/pos/admin/posMenuList");
        return mv;
	}
	
	// 메뉴 종류 데이터 호출
	@ResponseBody
	@RequestMapping(value = "/pos/posMenuKind")
	public DataMap posMenuKind(@DMap DataMap dataMap) throws Exception{
		// 코드 그룹 셋팅
		dataMap.put("CODE_GROUP", "MENU_KIND");
		List<DataMap> menuKind = posCommonService.selectCodeList(dataMap);
		
		dataMap.put("rows", menuKind);
        return dataMap;
	}
	
	// 메뉴 종류 데이터 처리(등록, 수정, 삭제)
	@ResponseBody
	@RequestMapping(value = "/pos/posMenuKindChange")
	public DataMap posMenuKindChange(@DMap DataMap dataMap) throws Exception{
		String target = dataMap.getString("oper");
		if(log.isDebugEnabled()){
			log.debug("========target:::::::[" + target + "]");
			log.debug("========requestMap.toString():::::::[" + dataMap.toString() + "]");
		}
		try {
			if("add".equals(target)){
				posCommonService.insertCode(dataMap);
			} else if("edit".equals(target)){
				posCommonService.updateCode(dataMap);
			} else if("del".equals(target)){
				posCommonService.deleteCode(dataMap);
			}
			dataMap.put("result", true);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("result", false);
			dataMap.put("errMsg", e.getMessage());
		}
        return dataMap;
	}
	
	// 메뉴 데이터 호출
	@ResponseBody
	@RequestMapping(value = "/pos/posMenuList")
	public DataMap posMenuList(@DMap DataMap dataMap) throws Exception{
		// 메뉴 가지고 옴
		List<DataMap> menuList = posMenuService.selectMenuList(dataMap);
		
		dataMap.put("rows", menuList);
        return dataMap;
	}
	
	// 메뉴 데이터 처리(등록, 수정, 삭제)
	@ResponseBody
	@RequestMapping(value = "/pos/posMenuChange")
	public DataMap posMenuChange(@DMap DataMap dataMap) throws Exception{
		String target = dataMap.getString("oper");
		if(log.isDebugEnabled()){
			log.debug("========target:::::::[" + target + "]");
			log.debug("========requestMap.toString():::::::[" + dataMap.toString() + "]");
		}
		try {
			if("add".equals(target)){
				posMenuService.insertMenu(dataMap);
			} else if("edit".equals(target)){
				posMenuService.updateMenu(dataMap);
			} else if("del".equals(target)){
				posMenuService.deleteMenu(dataMap);
			}
			dataMap.put("result", true);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("result", false);
			dataMap.put("errMsg", e.getMessage());
		}
        return dataMap;
	}
}
package com.pos.controller;

import java.util.ArrayList;
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
import com.pos.service.PosOrderService;


/**
 * Handles requests for the application home page.
 */
@Controller
public class PosOrderController {
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	// 메뉴 서비스
	@Autowired
	private PosOrderService posOrderService;
	// 메뉴코드 서비스
	@Autowired
	private PosCommonService posCommonService;
	
	// 메뉴 종류, 메뉴, 이전 주문정보 가져옴
	@RequestMapping(value = "/pos/posTableOrder")
	public ModelAndView posTableOrder(@DMap DataMap dataMap) throws Exception{
		ModelAndView mv = new ModelAndView("/pos/posTableOrder");
		// 코드 그룹 셋팅
		dataMap.put("sord", "ASC");
		dataMap.put("sidx", "CODE_PRIORITY");
		dataMap.put("CODE_GROUP", "MENU_KIND");
		// 메뉴 종류 가지고 옴
		List<DataMap> menuKind = posCommonService.selectCodeList(dataMap);
		// 메뉴 가지고 옴
		List<DataMap> menuList = posOrderService.selectMenuList(dataMap);
		// 해당 테이블 주문정보 조회
		DataMap orderInfo = posOrderService.getOrderInfo(dataMap);
		
		mv.addAllObjects(dataMap);
		mv.addObject("menuKind", menuKind);
		mv.addObject("menuList", menuList);
		mv.addObject("orderInfo", orderInfo);

        return mv;
	}
	// 이전 주문 메뉴 정보 가저옴
	@ResponseBody
	@RequestMapping(value = "/pos/posTableOrderMenu")
	public DataMap posTableOrderMenu(@DMap DataMap dataMap) throws Exception{
		List<DataMap> orderMenuInfo = posOrderService.getOrderMenuInfo(dataMap);
		dataMap.put("gridData", orderMenuInfo);
		return dataMap;
	}

	// 주문메뉴 넣기
	@ResponseBody
	@RequestMapping(value = "/pos/posOrder")
	public DataMap posOrder(@DMap DataMap dataMap) throws Exception{
		ArrayList<DataMap> orderMenuList = (ArrayList<DataMap>) dataMap.get("ArrayData");
		posOrderService.insertOrderMenu(orderMenuList);
        return dataMap;
	}
	// 주문메뉴 서비스
	@ResponseBody
	@RequestMapping(value = "/pos/posServiceOrderMenu")
	public DataMap posServiceOrderMenu(@DMap DataMap dataMap) throws Exception{
		posOrderService.updateServiceOrderMenu(dataMap);
		return dataMap;
	}
	// 주문메뉴 취소
	@ResponseBody
	@RequestMapping(value = "/pos/posDeleteOrderMenu")
	public DataMap posDeleteOrderMenu(@DMap DataMap dataMap) throws Exception{
		posOrderService.deleteOrderMenu(dataMap);
		return dataMap;
	}

	// 주문메뉴 전광판 조회
	@RequestMapping(value = "/pos/displayOrderList")
	public ModelAndView displayOrderList(@DMap DataMap dataMap) throws Exception{
		ModelAndView mv = new ModelAndView("/pos/orderList");
		List<DataMap> orderMenuList = posOrderService.getOrderList();
		mv.addObject("orderMenuList", orderMenuList);
		return mv;
	}
	
	// 결제하기
	@ResponseBody
	@RequestMapping(value = "/pos/paymentOrder")
	public DataMap paymentOrder(@DMap DataMap dataMap) throws Exception{
		posOrderService.insertPayment(dataMap);
		return dataMap;
	}
	// 주문 취소 하기
	@ResponseBody
	@RequestMapping(value = "/pos/cancelOrder")
	public DataMap cancelOrder(@DMap DataMap dataMap) throws Exception{
		posOrderService.updateCancelOrder(dataMap);
		return dataMap;
	}
	// 주문들어간 메뉴 조리 완료처리
	@ResponseBody
	@RequestMapping(value = "/pos/completeOrder")
	public DataMap completeOrder(@DMap DataMap dataMap) throws Exception{
		posOrderService.updateCompleteOrder(dataMap);
		return dataMap;
	}
}
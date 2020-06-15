package com.pos.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.core.common.map.DataMap;
import com.pos.service.PosPaymentService;


/**
 * Handles requests for the application home page.
 */
@Controller
public class PosPaymentController {
	Logger log = LoggerFactory.getLogger(this.getClass());
	// 결제 결과 서비스
	@Autowired
	private PosPaymentService posPaymentService;
	
	// 결제 이력 화면 호출
	@RequestMapping(value = "/pos/posPaymentListIndex")
	public ModelAndView posPaymentListIndex(DataMap dataMap) throws Exception{
		ModelAndView mv = new ModelAndView("/pos/admin/posPaymentList");
        return mv;
	}
	
	// 결제 이력 일자 데이터 호출
	@ResponseBody
	@RequestMapping(value = "/pos/posPaymentDate")
	public DataMap posPaymentDate(DataMap dataMap) throws Exception{
		List<DataMap> paymentDate = posPaymentService.selectPaymentDate(dataMap);
		dataMap.put("rows", paymentDate);
        return dataMap;
	}

	// 결제 이력 일자 데이터 상세 호출
	@ResponseBody
	@RequestMapping(value = "/pos/posPaymentDateDetail")
	public DataMap posPaymentDateDetail(DataMap dataMap) throws Exception{
		List<DataMap> paymentDateDetail = posPaymentService.selectPaymentDateDetail(dataMap);
		dataMap.put("rows", paymentDateDetail);
		return dataMap;
	}
	
	// 달력별 결제 데이터 호출
	@RequestMapping(value = "/pos/posSalesCalendar")
	public ModelAndView posSalesCalendar(DataMap dataMap) throws Exception{
		ModelAndView mv = new ModelAndView("/pos/admin/posSalesCalendar");
		// 아무것도 셋팅 되어 있지 않으면 조회조건 구분값과 오늘 날짜를 셋팅해준다.
		String target = StringUtils.defaultString(dataMap.getString("target"), "DAY");
		if(StringUtils.isEmpty(dataMap.getString("target"))){
			dataMap.put("target", target);
		}
		
		mv.addAllObjects(dataMap);
		
		if("DAY".equals(target)){
			// 일자별
			if(StringUtils.isEmpty(dataMap.getString("PAYMENT_DAY"))){
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
				dataMap.put("PAYMENT_DAY", sdf.format(date));
				mv.addAllObjects(dataMap);
			}
			String paymentDay = (String) dataMap.get("PAYMENT_DAY");
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, Integer.parseInt(paymentDay.replaceAll("[^\\d]", "").substring(0, 4)));
			cal.set(Calendar.MONTH, Integer.parseInt(paymentDay.replaceAll("[^\\d]", "").substring(4, 6))-1);
			cal.set(Calendar.DATE, 1);
			// 일요일은 1, 토요일은 7
			// 시작요일
			int startDay = cal.get(Calendar.DAY_OF_WEEK);
			// 마지막 일
			int lastDate = cal.getActualMaximum(Calendar.DATE);
			
			mv.addObject("startDay", startDay);
			mv.addObject("lastDate", lastDate);
		} else if("MONTH".equals(target)){
			// 월별
			if(StringUtils.isEmpty(dataMap.getString("PAYMENT_DAY"))){
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
				dataMap.put("PAYMENT_DAY", sdf.format(date));
			}
		}
		
		List<DataMap> salesDateList = posPaymentService.selectSalesCalendar(dataMap);
		
		// 데이터 가공
		DataMap salesDateMap = new DataMap();
		for (int i = 0; i < salesDateList.size(); i++) {
			int dbPaymentDay = salesDateList.get(i).getInt("PAYMENT_DAY");
			if(salesDateMap.get(dbPaymentDay) == null){
				DataMap tempMap = new DataMap();
				tempMap.put(salesDateList.get(i).getString("PAYMENT_KIND"), salesDateList.get(i).get("PAYMENT_AMOUNT"));
				salesDateMap.put(dbPaymentDay, tempMap);
			} else {
				DataMap tempMap = (DataMap)salesDateMap.get(dbPaymentDay);
				tempMap.put(salesDateList.get(i).getString("PAYMENT_KIND"), salesDateList.get(i).get("PAYMENT_AMOUNT"));
			}
		}
		log.debug(salesDateMap.toString());
		
		mv.addObject("salesDateMap", salesDateMap);
		mv.addObject("target", target);
        return mv;
	}
}
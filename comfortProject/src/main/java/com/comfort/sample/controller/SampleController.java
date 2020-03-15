package com.comfort.sample.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.comfort.sample.dto.SampleDto;
import com.comfort.sample.service.SampleService;


@Controller
public class SampleController {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private SampleService sampleService;
	
	@RequestMapping("/sample/openMenuList.do")
	public ModelAndView openMenuList() throws Exception {
		log.debug("sample start.......");
		ModelAndView mv = new ModelAndView("/sample/sampleList");
		
		List<SampleDto> list = sampleService.selectSampleList();
		mv.addObject("list", list);
		log.debug("sample end.......");
		return mv;
	}
}

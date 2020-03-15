package com.comfort.sample.service;

import java.util.List;

import com.comfort.sample.dto.SampleDto;

public interface SampleService {
	
	List<SampleDto> selectSampleList() throws Exception;
}

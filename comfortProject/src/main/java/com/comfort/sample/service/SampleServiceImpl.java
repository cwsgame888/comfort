package com.comfort.sample.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comfort.sample.dto.SampleDto;
import com.comfort.sample.mapper.SampleMapper;

@Service
public class SampleServiceImpl implements SampleService{

	@Autowired
	private SampleMapper sampleMapper;
	@Override
	public List<SampleDto> selectSampleList() throws Exception {
		// TODO Auto-generated method stub
		return sampleMapper.selectMenuListDto();
	}
}

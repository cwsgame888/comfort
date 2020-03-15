package com.comfort.sample.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.comfort.sample.dto.SampleDto;

@Mapper
public interface SampleMapper {
	List<SampleDto> selectMenuListDto() throws Exception;
}
package com.saveit.mapper;

import org.apache.ibatis.annotations.Param;

import com.saveit.vo.PromptVO;

public interface PromptMapper {

	PromptVO findByType(
			@Param("promptType")
			String promptType);

}

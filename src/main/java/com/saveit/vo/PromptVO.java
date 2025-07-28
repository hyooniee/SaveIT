package com.saveit.vo;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PromptVO {

	private int promptId;
	private String promptType;
	private String template;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	

}

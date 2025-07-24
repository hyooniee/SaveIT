package com.saveit.vo;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PromptHistory {
	private int historyId;
	private int userId;
	private int promptId;
	private String requestInput;
	private String geminiResponse;
	private boolean isSuccess;
	private LocalDateTime createdAt;
	
}

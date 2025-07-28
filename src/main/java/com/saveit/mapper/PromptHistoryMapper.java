package com.saveit.mapper;

import com.saveit.vo.PromptHistory;

public interface PromptHistoryMapper {

	PromptHistory findRecentSuccess(int userId, int promptId, String filledPrompt);

	void insertPromptHistory(PromptHistory history);

}

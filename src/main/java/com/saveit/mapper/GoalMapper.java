package com.saveit.mapper;

import com.saveit.vo.Goal;

public interface GoalMapper {

	Goal findGoalInfoByUserId(int userId);

}

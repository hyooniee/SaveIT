package com.saveit.mapper;

import java.time.LocalDate;

import org.apache.ibatis.annotations.Param;

import com.saveit.vo.Goal;

public interface GoalMapper {
	
	Goal findGoalInfoByUserId(int userId);

	Goal getGoalByUserId(@Param("userId") int userId);
	
	void saveGoalByUserId(@Param("userId") int userId,
						  @Param("goalAmount") int goalAmount,
						  @Param("goalDate") LocalDate goalDate);
	
	void deleteGoalByUserId(int userId);


}

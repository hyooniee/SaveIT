package com.saveit.mapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.saveit.vo.Expense;

public interface ExpenseMapper {

	

	List<Expense> getExpenseMonth(
		    @Param("userId") int userId,
		    @Param("startDate") LocalDate startDate,
		    @Param("endDate") LocalDate endDate
		);

	List<Map<String, Object>> getTop5CategoryExpense(
			@Param("userId") int userId, 
			@Param("startDate")LocalDate startDate, 
			@Param("endDate")LocalDate endDate);

	
}

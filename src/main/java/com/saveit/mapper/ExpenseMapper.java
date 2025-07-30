package com.saveit.mapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.saveit.vo.Expense;

public interface ExpenseMapper {

	List<Expense> getExpenseMonth(
		    @Param("userId") int userId,
		    @Param("startDate") LocalDate startDate,
		    @Param("endDate") LocalDate endDate
		);
	
	List<Expense> getExpenseWeek(
			@Param("userId") int userId,
			@Param("startDate") LocalDate startDate,
		    @Param("endDate") LocalDate endDate
		);
	
	
	@Select("SELECT category_id FROM tb_category WHERE category = #{category}")
	
	int findCategoryIdByName(@Param("category") String category);
	
	void inputExpense(@Param("userId") int userId,
						 @Param("categoryId") int categoryId,
						 @Param("amount") int amount,
						 @Param("expenseDate") LocalDate expenseDate);


	List<Map<String, Object>> getTop5CategoryExpense(
			@Param("userId") int userId, 
			@Param("startDate")LocalDate startDate, 
			@Param("endDate")LocalDate endDate);


	
}

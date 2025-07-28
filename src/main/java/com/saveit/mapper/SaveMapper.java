package com.saveit.mapper;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.saveit.vo.Save;

public interface SaveMapper {
	
	List<Save> getSaveWeek(
			@Param("userId") int userId,
			@Param("startDate") LocalDate startDate,
		    @Param("endDate") LocalDate endDate
		);
	
	@Select("SELECT category_id FROM tb_category WHERE category = #{category}")
	
	int findCategoryIdByName(@Param("category") String category);
	
	void inputSave(@Param("userId") int userId,
						 @Param("categoryId") int categoryId,
						 @Param("amount") int amount,
						 @Param("saveDate") LocalDate saveDate);
}
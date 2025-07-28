package com.saveit.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.saveit.mapper.SaveMapper;
import com.saveit.service.SaveService;
import com.saveit.vo.Save;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SaveServiceImpl implements SaveService{
	
	private final SaveMapper saveMapper;
	
	@Override
	public List<Save> getSave(int userId, int offset) {
		LocalDate baseDate = LocalDate.now().plusDays(offset);
	    LocalDate startDate = baseDate.minusDays(2);
	    LocalDate endDate = baseDate.plusDays(2);
		
		return saveMapper.getSaveWeek(userId, startDate, endDate);
	}

	@Override
	public Save inputSave(Save save) {
		
		int categoryId = saveMapper.findCategoryIdByName(save.getCategory());
		
		saveMapper.inputSave(
				save.getUserId(),
				categoryId,
				save.getAmount(),
				save.getSaveDate()
			);
		
		return save;
	}
}

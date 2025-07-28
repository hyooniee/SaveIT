package com.saveit.service;

import java.util.List;

import com.saveit.vo.Save;

public interface SaveService {
	List<Save> getSave(int userId, int offset);
	Save inputSave(Save save);
	
}

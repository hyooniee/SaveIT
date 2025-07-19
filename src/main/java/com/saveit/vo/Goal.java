package com.saveit.vo;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
//@AllArgsConstructor
public class Goal {
	
		private int userId;
	    private int goalAmount;
	    private LocalDate goalDate;
		
	    // getter, setter
	
	    

}

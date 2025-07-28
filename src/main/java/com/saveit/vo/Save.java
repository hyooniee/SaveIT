package com.saveit.vo;

import java.time.LocalDate;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Save {
    private Integer userId;         // save.user_id
    private Integer kind;           // cate.kind
    private String category;        // cate.category
    private LocalDate saveDate;  	// save.saveing_date
    private Integer amount;         // save.amount
	
}

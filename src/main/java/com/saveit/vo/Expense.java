package com.saveit.vo;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Expense {
    private Integer userId;         // exp.user_id
    private Integer kind;           // cate.kind
    private String category;        // cate.category
    private LocalDate expenseDate;  // exp.expense_date
    private Integer amount;         // exp.amount
    // + getter/setter
}

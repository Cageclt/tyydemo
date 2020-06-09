package com.tyy.pojo.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bill {
    private int BillId;
    private int UserId;
    private String Title;
    private Date Time;
    private int Sum;
}

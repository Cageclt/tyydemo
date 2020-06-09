package com.tyy.pojo.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhoneCode {
    private int UserId;
    private int Code;
    private String Phone;
    private Date Time;
}

package com.tyy.pojo.Article;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class R_Review {
    private int ReviewId;
    private int UserId;
    private int TargetId;
    private String Reviews;
    private Date Time;
    private int Good;
}

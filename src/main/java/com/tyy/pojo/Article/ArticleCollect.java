package com.tyy.pojo.Article;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleCollect {
    private int Id;
    private int UserId;
    private int ArticleId;
}

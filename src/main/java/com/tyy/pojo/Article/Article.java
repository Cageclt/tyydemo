package com.tyy.pojo.Article;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    private int ArticleId;//文章ID
    private int UserId;//所属用户ID
    private String Title;//文章标题
    private String Content;//文章内容
    private int View;//阅读量
    private int Good;//点赞量
    private int Collection;//收藏量
    private int Share;//分享量
    private int ReviewNum;//评论数
    private Date Time;//发布时间
    private int BillId;//若有绑定账单，对应的账单ID
    private String Location;//出游地点
    private String Is_Choicely;//是否为精选游记，是的话为T，否为F
    private String Is_GoodKid;//是否为小当家游记，是的话为T，否为F
    private String Summary;//一句话题记
}

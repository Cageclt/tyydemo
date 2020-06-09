package com.tyy.service.Initialize;

import com.tyy.pojo.Article.Article;
import com.tyy.pojo.Utils.PageBean;

public interface InitializeService {

    //首页初始化分页检索精选游记
    PageBean<Article> initializeIsChoicelyArticleIndex(int Count, int Page);

    //首页初始化分页检索小当家游记
    PageBean<Article> initializeIsGoodKidArticleIndex(int Count,int Page);

    //首页初始化分页检索好友游记
    PageBean<Article> initializeFriendArticleIndex(int UserId,int Count,int Page);

    //首页初始化分页检索身边动态
    PageBean<Article> initializeAroundArticleIndex(int UserId,int Count,int Page);
}

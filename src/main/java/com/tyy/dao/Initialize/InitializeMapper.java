package com.tyy.dao.Initialize;

import com.tyy.pojo.Article.Article;
import com.tyy.pojo.User.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface InitializeMapper {
    //检索精选游记的数目
    int queryIsChoicelyCount();

    //首页初始化分页检索精选游记
    List<Article> initializeIsChoicelyArticleIndex(Map <String,Object> map);

    //检索小当家游记的数目
    int queryIsGoodKidCount();

    //首页初始化分页检索小当家游记
    List<Article> initializeIsGoodKidArticleIndex(Map <String,Object> map);

    //检索好友游记的数目
    int queryFriendArticleCount(@Param("UserId")int id);

    //首页初始化分页检索好友游记
    List<Article> initializeFriendArticleIndex(Map <String,Object> map);

    //检索身边动态的数目
    int queryAroundArticleCount(@Param("UserId")int id);

    //首页初始化分页检索身边动态
    List<Article> initializeAroundArticleIndex(Map <String,Object> map);

}

package com.tyy.dao.Article;

import com.tyy.pojo.Article.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ArticleMapper {
    //搜索框模糊检索游记
    List<Article> queryArticleByNameLike(@Param("Title") String title);

    //查看游记详情
    Article queryArticleById(@Param("ArticleId") int articleId);

    //更改游记浏览量
    int addArticleView(@Param("ArticleId") int articleId);

    //为游记添加评论
    int addReview(Map<String,Object> map);

    //点赞游记评论
    int addReviewGood(@Param("ReviewId")int ReviewId);

    //增加游记评论数
    int addReviewNum(@Param("ArticleId") int articleId);

    //添加楼中楼评论
    int addR_Review(Map<String,Object> map);

    //点赞楼中楼评论
    int addR_ReviewGood(@Param("ReviewId")int ReviewId);

    //点赞游记
    int addArticleGood(@Param("ArticleId") int articleId);

    //发布游记
    int addArticle(Map<String,Object> map);

    //收藏一篇游记
    int addArticleCollect(@Param("UserId")int userid,@Param("ArticleId") int articleid);

    //增加游记收藏数
    int addArticleCollection(@Param("ArticleId") int articleId);


}

package com.tyy.service.Article;

import com.tyy.dao.Article.ArticleMapper;
import com.tyy.pojo.Article.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("ArticleServiceImpl")
public class ArticleServiceImpl implements ArticleService{
    @Autowired
    //service层调DAO层
    private ArticleMapper articleMapper;

    public void setArticleMapper(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    //搜索框模糊检索游记
    public List<Article> queryArticleByNameLike(String title) {
        return articleMapper.queryArticleByNameLike(title);
    }

    //查看游记详情
    public Article queryArticleById(int articleId) {
        return articleMapper.queryArticleById(articleId);
    }

    //更改游记浏览量
    public int addArticleView(int articleId) {
        return articleMapper.addArticleView(articleId);
    }

    //为游记添加评论
    public int addReview(Map<String, Object> map) {
        Date nowdate = new Date();
        SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.format(nowdate);
        map.put("Time",nowdate);
        map.put("Good",0);
        return articleMapper.addReview(map);
    }

    //点赞游记评论
    public int addReviewGood(int ReviewId) {
        return articleMapper.addReviewGood(ReviewId);
    }

    //增加游记评论数
    public int addReviewNum(int articleId) {
        return articleMapper.addReviewNum(articleId);
    }

    //添加楼中楼评论
    public int addR_Review(Map<String, Object> map) {
        Date nowdate = new Date();
        SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.format(nowdate);
        map.put("Time",nowdate);
        map.put("Good",0);
        return articleMapper.addR_Review(map);
    }

    //点赞楼中楼评论
    public int addR_ReviewGood(int ReviewId) {
        return articleMapper.addR_ReviewGood(ReviewId);
    }

    //点赞游记
    public int addArticleGood(int articleId) {
        return articleMapper.addArticleGood(articleId);
    }

    //发布游记
    public int addArticle(Map<String, Object> map) {
        return articleMapper.addArticle(map);
    }

    //收藏一篇游记
    public int addArticleCollect(int userid, int articleid) {
        return articleMapper.addArticleCollect(userid, articleid);
    }
    //增加游记收藏数
    public int addArticleCollection(int articleId) {
        return articleMapper.addArticleCollection(articleId);
    }
}

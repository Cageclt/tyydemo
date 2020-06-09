package com.tyy.controller;

import com.alibaba.fastjson.JSON;
import com.tyy.pojo.Utils.BaseResponse;
import com.tyy.pojo.Utils.StatusCode;
import com.tyy.service.Article.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController("ArticleController")
public class ArticleController {
    //controller层调service层
    @Autowired
    private ArticleService articleService;

    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }

    @RequestMapping("/addArticleGood")
    //点赞游记
    public String addArticleGood(@RequestParam("ArticleId") int articleId){
        BaseResponse response=new BaseResponse(StatusCode.Fail);
        int status = articleService.addArticleGood(articleId);
        if (status != 0){
            response.setCode(0);
            response.setMsg("成功");
            response.setData(status);
            String result = JSON.toJSONString(response);
            return result;
        }
        else{
            String result = JSON.toJSONString(response);
            return  result;
        }
    }

    @RequestMapping("/addReviewGood")
    //点赞评论
    public String addReviewGood(@RequestParam("ReviewId") int ReviewId){
        BaseResponse response=new BaseResponse(StatusCode.Fail);
        int status = articleService.addReviewGood(ReviewId);
        if (status != 0){
            response.setCode(0);
            response.setMsg("成功");
            response.setData(status);
            String result = JSON.toJSONString(response);
            return result;
        }
        else{
            String result = JSON.toJSONString(response);
            return  result;
        }
    }

    @RequestMapping("/addR_ReviewGood")
    //点赞楼中楼评论
    public String addR_ReviewGood(@RequestParam("ReviewId") int ReviewId){
        BaseResponse response=new BaseResponse(StatusCode.Fail);
        int status = articleService.addR_ReviewGood(ReviewId);
        if (status != 0){
            response.setCode(0);
            response.setMsg("成功");
            response.setData(status);
            String result = JSON.toJSONString(response);
            return result;
        }
        else{
            String result = JSON.toJSONString(response);
            return  result;
        }
    }

    @RequestMapping("/addReview")
    //评论游记
    public String addReview(@RequestParam("DataMap") Map<String, Object> Datamap,@RequestParam("ArticleId") int articleId){
        BaseResponse response=new BaseResponse(StatusCode.Fail);
        int status = articleService.addReview(Datamap);
        if (status != 0){
            articleService.addReviewNum(articleId);
            response.setCode(0);
            response.setMsg("成功");
            response.setData(Datamap);
            String result = JSON.toJSONString(response);
            return result;
        }
        else{
            String result = JSON.toJSONString(response);
            return  result;
        }
    }

    @RequestMapping("/addR_Review")
    //添加楼中楼评论
    public String addR_Review(@RequestParam("DataMap") Map<String, Object> Datamap){
        BaseResponse response=new BaseResponse(StatusCode.Fail);
        int status = articleService.addR_Review(Datamap);
        if (status != 0){
            response.setCode(0);
            response.setMsg("成功");
            response.setData(Datamap);
            String result = JSON.toJSONString(response);
            return result;
        }
        else{
            String result = JSON.toJSONString(response);
            return  result;
        }
    }



    @RequestMapping("/collect")
    //收藏游记
    public String addArticleCollect(@RequestParam("UserId")int userid, @RequestParam("ArticleId")int articleid) {
        BaseResponse response=new BaseResponse(StatusCode.Fail);
        int status = articleService.addArticleCollect(userid, articleid);
        if (status != 0){
            articleService.addArticleCollection(articleid);
            response.setCode(0);
            response.setMsg("成功");
            response.setData(status);
            String result = JSON.toJSONString(response);
            return result;
        }
        else{
            String result = JSON.toJSONString(response);
            return  result;
        }
    }




}

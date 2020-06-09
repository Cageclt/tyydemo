package com.tyy.controller;


import com.alibaba.fastjson.JSON;
import com.tyy.pojo.Article.Article;
import com.tyy.pojo.User.UserInfo;
import com.tyy.pojo.Utils.BaseResponse;
import com.tyy.pojo.Utils.PageBean;
import com.tyy.pojo.Utils.StatusCode;
import com.tyy.service.Initialize.InitializeService;
import com.tyy.service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController("InitializeController")
public class InitializeController {
    @Autowired
    private InitializeService initializeService;
    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setInitializeService(InitializeService initializeService) {
        this.initializeService = initializeService;
    }

    @RequestMapping("/index")
        //进入主页进行初始化数据填充
        public String indexInitialize(@RequestParam("UserId") int userid, @RequestParam("Count") int count){
            BaseResponse response=new BaseResponse(StatusCode.Fail);
            //初始化精选游记
            PageBean<Article> IsChoicelyArticle = new PageBean<Article>();
            IsChoicelyArticle = initializeService.initializeIsChoicelyArticleIndex(count,2);
            //初始化小当家游记
            PageBean<Article>  IsGoodKidArticle = new PageBean<Article>();
            IsGoodKidArticle = initializeService.initializeIsGoodKidArticleIndex(count,2);
            //初始化好友动态
            PageBean<Article>  FriendArticle = new PageBean<Article>();
            FriendArticle = initializeService.initializeFriendArticleIndex(userid,count,2);
            //初始化身边动态
            PageBean<Article>  AroundArticle = new PageBean<Article>();
            AroundArticle = initializeService.initializeAroundArticleIndex(userid, count,2);
            //封装成map
            Map<String, Object> Datamap = new HashMap<String, Object>();
            Datamap.put("IsChoicelyArticle",IsChoicelyArticle);
            Datamap.put("IsGoodKidArticle",IsGoodKidArticle);
            Datamap.put("FriendArticle",FriendArticle);
            Datamap.put("AroundArticle",AroundArticle);
            if(Datamap != null && Datamap.isEmpty()!= true ){
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


    @RequestMapping("/ground")
    //进入游记广场进行初始化数据填充，刷新时重复这一方法
    public String groundInitialize(@RequestParam("UserId") int userid,@RequestParam("Count") int count){
        BaseResponse response=new BaseResponse(StatusCode.Fail);
        //初始化精选游记
        PageBean<Article> IsChoicelyArticle = new PageBean<Article>();
        IsChoicelyArticle = initializeService.initializeIsChoicelyArticleIndex(count,6);
        //初始化小当家游记
        PageBean<Article>  IsGoodKidArticle = new PageBean<Article>();
        IsGoodKidArticle = initializeService.initializeIsGoodKidArticleIndex(count,6);
        //初始化好友动态
        PageBean<Article>  FriendArticle = new PageBean<Article>();
        FriendArticle = initializeService.initializeFriendArticleIndex(userid,count,6);
        //初始化身边动态
        PageBean<Article>  AroundArticle = new PageBean<Article>();
        AroundArticle = initializeService.initializeAroundArticleIndex(userid, count,6);
        //封装成map
        Map<String, Object> Datamap = new HashMap<String, Object>();
        Datamap.put("IsChoicelyArticle",IsChoicelyArticle);
        Datamap.put("IsGoodKidArticle",IsGoodKidArticle);
        Datamap.put("FriendArticle",FriendArticle);
        Datamap.put("AroundArticle",AroundArticle);
        if(Datamap != null && Datamap.isEmpty()!= true ){
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

    @RequestMapping("/Another")//进入他人主页
    public String anotherInitialize(@RequestParam("UserId") int UserId){
        BaseResponse response=new BaseResponse(StatusCode.Fail);
        List<Article> Articlelist = new ArrayList<Article>();
        Articlelist = userService.queryAllArticleById(UserId);//首先查询用户的所有游记
        UserInfo userInfo = new UserInfo();
        userInfo = userService.queryUserInfoById(UserId);//查询用户个人信息
        int ArticleNum = userService.queryArticleNumById(UserId);//查询用户文章数
        int GoodNum = userService.queryGoodNumById(UserId);//查询总获赞量

        //封装成map
        Map<String, Object> Datamap = new HashMap<String, Object>();
        Datamap.put("Articlelist",Articlelist);
        Datamap.put("UserInfo",userInfo);
        Datamap.put("ArticleNum",ArticleNum);
        Datamap.put("GoodNum",GoodNum);

        if(Datamap != null && Datamap.isEmpty()!= true ){
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

    @RequestMapping("/Personal")
    //进入个人主页进行初始化数据填充
    public String personalInitialize(@RequestParam("UserId") int UserId) {
        BaseResponse response = new BaseResponse(StatusCode.Fail);
        List<Article> Articlelist = new ArrayList<Article>();
        Articlelist = userService.queryAllArticleById(UserId);//首先查询用户的所有游记
        UserInfo userInfo = new UserInfo();
        userInfo = userService.queryUserInfoById(UserId);//查询用户个人信息
        int ArticleNum = userService.queryArticleNumById(UserId);//查询用户文章数
        int GoodNum = userService.queryGoodNumById(UserId);//查询总获赞量

        //封装成map
        Map<String, Object> Datamap = new HashMap<String, Object>();
        Datamap.put("Articlelist", Articlelist);
        Datamap.put("UserInfo", userInfo);
        Datamap.put("ArticleNum", ArticleNum);
        Datamap.put("GoodNum", GoodNum);

        if (Datamap != null && Datamap.isEmpty() != true) {
            response.setCode(0);
            response.setMsg("成功");
            response.setData(Datamap);
            String result = JSON.toJSONString(response);
            return result;
        } else {
            String result = JSON.toJSONString(response);
            return result;
        }
    }
}
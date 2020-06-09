package com.tyy.service.Initialize;

import com.tyy.dao.Initialize.InitializeMapper;
import com.tyy.dao.User.UserMapper;
import com.tyy.pojo.Article.Article;
import com.tyy.pojo.Utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service("InitializeServiceImpl")
public class InitializeServiceImpl implements InitializeService{
    @Autowired
    private InitializeMapper initializeMapper;

    public void setInitializeMapper(InitializeMapper initializeMapper) {
        this.initializeMapper = initializeMapper;
    }

    //首页初始化分页检索精选游记
    public PageBean<Article> initializeIsChoicelyArticleIndex(int Count, int Page) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        PageBean<Article> pageBean = new PageBean<Article>();
        //封装当前页数
        pageBean.setCurrPage(Count);
        //每页显示的数据
        int pageSize = Page;
        pageBean.setPageSize(pageSize);
        //封装总记录数
        int totalCount = initializeMapper.queryIsChoicelyCount();
        pageBean.setTotalCount(totalCount);

        //封装总页数
        double tc = totalCount;
        Double num = Math.ceil(tc / pageSize);//向上取整,除不尽的时候都向上取整
        pageBean.setTotalPage(num.intValue());

        map.put("start", (Count - 1) * pageSize);
        map.put("size", pageBean.getPageSize());
        //封装每页显示的数据
        List<Article> lists = initializeMapper.initializeIsChoicelyArticleIndex(map);
        pageBean.setLists(lists);
        return pageBean;

    }

    //首页初始化分页检索小当家游记
    public PageBean<Article> initializeIsGoodKidArticleIndex(int Count,int Page) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        PageBean<Article> pageBean = new PageBean<Article>();
        //封装当前页数
        pageBean.setCurrPage(Count);

        //每页显示的数据
        int pageSize = Page;
        pageBean.setPageSize(pageSize);

        //封装总记录数
        int totalCount = initializeMapper.queryIsGoodKidCount();
        pageBean.setTotalCount(totalCount);

        //封装总页数
        double tc = totalCount;
        Double num = Math.ceil(tc / pageSize);//向上取整,除不尽的时候都向上取整
        pageBean.setTotalPage(num.intValue());

        map.put("start", (Count - 1) * pageSize);
        map.put("size", pageBean.getPageSize());
        //封装每页显示的数据
        List<Article> lists = initializeMapper.initializeIsGoodKidArticleIndex(map);
        pageBean.setLists(lists);
        return pageBean;
    }

    //首页初始化分页检索好友游记
    public PageBean<Article> initializeFriendArticleIndex(int UserId,int Count,int Page) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        PageBean<Article> pageBean = new PageBean<Article>();
        //封装当前页数
        pageBean.setCurrPage(Count);

        //每页显示的数据
        int pageSize = Page;
        pageBean.setPageSize(pageSize);

        //封装总记录数
        int totalCount = initializeMapper.queryFriendArticleCount(UserId);
        pageBean.setTotalCount(totalCount);

        //封装总页数
        double tc = totalCount;
        Double num = Math.ceil(tc / pageSize);//向上取整,除不尽的时候都向上取整
        pageBean.setTotalPage(num.intValue());

        map.put("start", (Count - 1) * pageSize);
        map.put("size", pageBean.getPageSize());
        map.put("UserId",UserId);
        //封装每页显示的数据
        List<Article> lists = initializeMapper.initializeFriendArticleIndex(map);
        pageBean.setLists(lists);
        return pageBean;
    }

    //首页初始化分页检索身边动态
    public PageBean<Article> initializeAroundArticleIndex(int UserId,int Count,int Page) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        PageBean<Article> pageBean = new PageBean<Article>();
        //封装当前页数
        pageBean.setCurrPage(Count);

        //每页显示的数据
        int pageSize = Page;
        pageBean.setPageSize(pageSize);

        //封装总记录数
        int totalCount = initializeMapper.queryAroundArticleCount(UserId);
        pageBean.setTotalCount(totalCount);

        //封装总页数
        double tc = totalCount;
        Double num = Math.ceil(tc / pageSize);//向上取整,除不尽的时候都向上取整
        pageBean.setTotalPage(num.intValue());

        map.put("start", (Count - 1) * pageSize);
        map.put("size", pageBean.getPageSize());
        map.put("UserId",UserId);
        //封装每页显示的数据
        List<Article> lists = initializeMapper.initializeAroundArticleIndex(map);
        pageBean.setLists(lists);
        return pageBean;
    }
}

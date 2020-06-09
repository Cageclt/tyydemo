package com.tyy.service.User;

import com.tyy.pojo.Article.Article;
import com.tyy.pojo.Utils.PageBean;
import com.tyy.pojo.User.*;

import java.util.List;
import java.util.Map;

public interface UserService {
    //增加一个用户
    int addUser(int UserId);

    //添加用户信息
    int addUserInfo(int UserId);

    //更新用户手机号
    int updateUserPhone(Map <String,Object> map);

    //根据ID查询用户
    User queryUserById(int id);

    //根据ID查询用户信息
    UserInfo queryUserInfoById(int id);

    //查询所有的用户
    List<User> queryAllUser();

    //根据用户名模糊查询用户
    List<User> queryUserByNameLike(String name);

    //根据用户名模糊查询用户信息
    List<UserInfo> queryUserInfoByNameLike(String name);

    //更新用户身份信息
    int updateUserType(int id,String userType);

    //确认亲子关系后添加用户关系条目
    int addRelation(Map<String,Object> map);

    //完善、修改个人信息
    int updateUserInfo(Map <String,Object> map);

    //查看自己的好友
    List<Friend> queryAllFriend(int id);

    //用户更新手机号，发送短信验证码
    boolean sendCode(int UserId,String Phone);

    //用户收到短信后，对输入的验证码进行验证
    PhoneCode checkingCode(int id,int code);

    //关注好友
    int addFriend(int id,int targetId);

    //检索个人游记
    List<Article> queryAllArticleById(int id);

    //记录旅游目标
    int updateNewScenery(int id,String NewScenery);

    //检索自己收藏的游记
    List<Article> queryAllArticleCollectById(int id);


    //计算个人页面的文章数
    int queryArticleNumById(int id);

    //计算个人页面的获赞数
    int queryGoodNumById(int id);

    //向数据库中插入一条图片资源记录
    int addPicture(String PictureName);

}

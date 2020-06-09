package com.tyy.dao.User;

import com.tyy.pojo.Article.Article;
import com.tyy.pojo.User.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    //增加一个用户
    int addUser(@Param("UserId") int id);

    //添加用户信息
    int addUserInfo(Map <String,Object> map);

    //更新用户表内的手机号
    int updateUserPhone(Map <String,Object> map);

    //更新用户信息表内的手机号
    int updateUserInfoPhone(Map <String,Object> map);

    //根据ID查询用户
    User queryUserById(@Param("UserId") int id);

    //根据ID查询用户信息
    UserInfo queryUserInfoById(@Param("UserId") int id);

    //查询所有的用户
    List<User> queryAllUser();

    //根据用户名模糊查询用户
    List<User> queryUserByNameLike(@Param("Name") String name);

    //根据用户名模糊查询用户信息
    List<UserInfo> queryUserInfoByNameLike(@Param("Name") String name);

    //更新用户身份信息
    int updateUserType(@Param("UserId") int id,@Param("UserType") String userType);

    //确认亲子关系后添加用户关系条目
    int addRelation(Map <String,Object> map);

    //完善、修改个人信息
    int updateUserInfo(Map <String,Object> map);

    //修改个人信息后保持好友表信息相同
    int friendtrigger(Map <String,Object> map);

    //查看自己的好友
    List<Friend> queryAllFriend(@Param("UserId")int id);

    //用户换绑手机号，生成随机验证码
    int createRandomCode(PhoneCode phoneCode);

    //用户换绑手机号，输入验证码后验证
    PhoneCode checkingCode(@Param("UserId")int id,@Param("Code")int code);

    //关注好友
    int addFriend(Map <String,Object> map);

    //检索个人游记
    List<Article> queryAllArticleById(@Param("UserId")int id);


    //记录旅游目标
    int updateNewScenery(@Param("UserId") int id,@Param("NewScenery") String NewScenery);

    //检索自己收藏的游记
    List<Article> queryAllArticleCollectById(@Param("UserId")int id);


    //计算个人页面的文章数
    int queryArticleNumById(@Param("UserId")int id);

    //计算个人页面的获赞数
    int queryGoodNumById(@Param("UserId")int id);

    //向数据库中插入一条图片资源记录
    int addPicture(@Param("PictureName")String PictureName);

}

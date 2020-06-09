package com.tyy.service.User;

import com.tyy.dao.User.UserMapper;
import com.tyy.pojo.Article.Article;
import com.tyy.pojo.Utils.PageBean;
import com.tyy.pojo.User.*;
import com.tyy.pojo.Utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("UserServiceImpl")
public class UserServiceImpl implements UserService{
@Autowired
    //service层调DAO层
    private UserMapper userMapper;

    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public int addUser(int UserId) {
        return userMapper.addUser(UserId);
    }

    @Override
    public int addUserInfo(int UserId) {
        Map<String, Object> map = new HashMap<String, Object>();//将要传给dao层的所有参数打包为map
        Date nowdate = new Date();
        SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.format(nowdate);//生成当前时间
        map.put("UserId",UserId);
        map.put("Sex","M");
        map.put("RegisterTime",nowdate);
        map.put("UserType","C");
        map.put("Good",0);
        map.put("ArticleNum",0);
        return userMapper.addUserInfo(map);
    }

    public int updateUserPhone(Map<String, Object> map) {
        int num = 0;
        int a = userMapper.updateUserInfoPhone(map);
        int b =userMapper.updateUserPhone(map);
        num=a+b;
        return num;
    }

    public User queryUserById(int id) {
        return userMapper.queryUserById(id);
    }

    public UserInfo queryUserInfoById(int id) {
        return userMapper.queryUserInfoById(id);
    }

    public List<User> queryAllUser() {
        return userMapper.queryAllUser();
    }

    public List<User> queryUserByNameLike(String name) {
        return userMapper.queryUserByNameLike(name);
    }

    public List<UserInfo> queryUserInfoByNameLike(String name) {
        return userMapper.queryUserInfoByNameLike(name);
    }

    public int updateUserType(int id, String userType) {
        return userMapper.updateUserType(id, userType);
    }

    //确认亲子关系后添加用户关系条目
    public int addRelation(Map<String, Object> map) {
        return userMapper.addRelation(map);
    }

    //修改个人信息
    public int updateUserInfo(Map<String, Object> map) {
        int re = userMapper.updateUserInfo(map);
        if(re==1);
        userMapper.friendtrigger(map);
        return re;
    }

    //查看自己的好友
    public List<Friend> queryAllFriend(int id) {
        return userMapper.queryAllFriend(id);
    }

    //用户更新手机号，发送短信验证码
    public boolean sendCode(int UserId,String Phone) {
        int random = (int)((Math.random()*9+1)*100000);//生成6位随机数作验证码
        Date nowdate = new Date();
        SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.format(nowdate);//生成当前时间
        HashMap<String, Object> param = new HashMap<>();
        param.put("code",random);
        boolean suceeess = Utils.sendSms(Phone,"SMS_189836374",param);//调用工具类发送短信
        if (suceeess){//若短信成功则向数据库插入一条记录
            PhoneCode phoneCode = new PhoneCode();//对象封装
            phoneCode.setUserId(UserId);
            phoneCode.setPhone(Phone);
            phoneCode.setTime(nowdate);
            phoneCode.setCode(random);//生成6位随机数作验证码
            if(userMapper.createRandomCode(phoneCode)!=0)
            return true;
        }
            return false;
    }

    //用户收到短信后，对输入的验证码进行验证
    public PhoneCode checkingCode(int UserId,int code){
        return userMapper.checkingCode(UserId, code);
    }

    //关注好友
    public int addFriend(int id, int targetId) {
        UserInfo userInfo = userMapper.queryUserInfoById(targetId);//以targetId检索到要关注的好友信息
        Map<String, Object> map = new HashMap<String, Object>();//将要传给dao层的所有参数打包为map
        map.put("UserId",id);
        map.put("TargetId",targetId);
        map.put("Note",userInfo.getName());
        map.put("Photo",userInfo.getPhoto());
        map.put("Signature",userInfo.getSignature());
        map.put("NowScenery",userInfo.getNowScenery());
        return userMapper.addFriend(map);
    }


    //检索旅途记录
    public List<Article> queryAllArticleById(int id) {
        return userMapper.queryAllArticleById(id);//直接根据UserId查询该用户下的所有游记
    }


    public int updateNewScenery(int id, String NewScenery) {
        return userMapper.updateNewScenery(id, NewScenery);
    }

    public List<Article> queryAllArticleCollectById(int id) {
        return userMapper.queryAllArticleCollectById(id);
    }


    //计算个人页面的文章数
    public int queryArticleNumById(int id) {
        return userMapper.queryArticleNumById(id);
    }

    public int queryGoodNumById(int id) {
        return userMapper.queryGoodNumById(id);
    }

    @Override
    public int addPicture(String PictureName) {
        return userMapper.addPicture(PictureName);
    }


}

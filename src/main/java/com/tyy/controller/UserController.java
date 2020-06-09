package com.tyy.controller;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.utils.StringUtils;
import com.tyy.pojo.Article.Article;
import com.tyy.pojo.User.*;
import com.tyy.pojo.Utils.*;
import com.tyy.service.Article.ArticleService;
import com.tyy.service.User.UserService;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.commons.httpclient.HttpClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController ("UserController")
public class UserController {
    //controller层调service层
    @Autowired
    private UserService userService;
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    private ArticleService articleService;
    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }

    private static final Logger logger = Logger.getLogger(UserController.class);

    /**
     * 小程序登录
     * @param code
     * @return
     */

    @RequestMapping("/login")
    public String login(@RequestParam(required = false) String code){
        BaseResponse response=new BaseResponse(StatusCode.Fail);
        try {
            logger.info("[登录参数]:code = "+code);

            if(StringUtils.isEmpty(code)){
                response.setMsg("缺少必要参数");
                String result = JSON.toJSONString(response);
               return result;
            }


            String url = "https://api.weixin.qq.com/sns/jscode2session?appid="+wxAppid+"&secret="+wxSecret+"&js_code="+code+"&grant_type=authorization_code";
            logger.info("调用微信登录接口,参数:["+url+"]");
            OkHttpUtil okHttpUtil = new OkHttpUtil();
            String wxResponse = okHttpUtil.get(url);//向微信服务器发送请求
            logger.info("调用微信登录接口,code:["+code+"],返回数据:["+wxResponse+"]");
            if(StringUtils.isEmpty(wxResponse)){
                logger.error("调用微信登录接口错误,code:["+code+"],返回数据为空");
                response.setMsg("登录失败");
                String result = JSON.toJSONString(response);
                return result;
            }
            Map<String,Object> resultMap = (Map<String, Object>) JSON.parse(wxResponse);
            if(resultMap.containsKey("errcode")){
                logger.error("调用微信登录接口错误,code:["+code+"],返回数据:["+wxResponse+"]");
                response.setMsg("登录失败");
                String result = JSON.toJSONString(response);
                return result;
            }
//            String wxResponse = "test";
//            Map<String,Object> resultMap = new HashMap<>();
//            resultMap.put("openid","oJYeX5D-EUEthnSaxAZGr0j9nbeg");
//            resultMap.put("session_key","123");



            User user = new User();
            int openid ;
            String sessionKey = null;
            if(resultMap.containsKey("openid") && resultMap.containsKey("session_key")){
                openid = (int) resultMap.get("openid");
                sessionKey = (String) resultMap.get("session_key");
            }else{
                logger.error("调用微信登录接口错误,code:["+code+"],返回数据:["+wxResponse+"]");
                response.setMsg("openid为空");
                String result = JSON.toJSONString(response);
                return result;
            }

            user.setUserId(openid);

            Integer userId = null;
            user = userService.queryUserById(openid);
            if(user == null){
                user.setUserId(openid);
                userService.addUser(openid);//此处使用分配到的openid作为数据库中的用户主键ID以区分唯一性
                userService.addUserInfo(openid);
                response.setCode(0);
                response.setMsg("登录成功");
                response.setData(userService.queryUserInfoById(openid));
                String result = JSON.toJSONString(response);
                return result;
            }else{
                response.setCode(0);
                response.setMsg("登录成功");
                response.setData(userService.queryUserInfoById(openid));
                String result = JSON.toJSONString(response);
                return result;
            }

        }
        catch (Exception e){
            e.printStackTrace();
            logger.error("[登录异常]");
            String result = JSON.toJSONString(response);
            return result;
        }
    }



    @RequestMapping("/search")//搜索框搜索用户与游记
    public String indexSearch(@RequestParam("Text") String Text){
        BaseResponse response=new BaseResponse(StatusCode.Fail);

        List<UserInfo> UserInfolist = new ArrayList<UserInfo>();
        UserInfolist = userService.queryUserInfoByNameLike(Text);

        List<Article> Articlelist = new ArrayList<Article>();
        Articlelist = articleService.queryArticleByNameLike(Text);

        //封装成map
        Map<String, Object> Datamap = new HashMap<String, Object>();
        Datamap.put("UserInfolist",UserInfolist);
        Datamap.put("Articlelist",Articlelist);

        if(Datamap != null && !Datamap.isEmpty() ){
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


    @RequestMapping("/addFriend")//他人主页点击关注好友
    public String addFriend(@RequestParam("UserId")int UserId,@RequestParam("TargetId")int TargetId){
        BaseResponse response=new BaseResponse(StatusCode.Fail);
        int status = userService.addFriend(UserId, TargetId);
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

    @RequestMapping("/showCollect")
    //展示好友列表及文章收藏列表
    public String showCollect(@RequestParam("UserId") int UserId){
        BaseResponse response=new BaseResponse(StatusCode.Fail);
        List<Article> Articlelist = new ArrayList<Article>();
        List<Friend> Friendlist = new ArrayList<Friend>();
        Articlelist = userService.queryAllArticleCollectById(UserId);
        Friendlist = userService.queryAllFriend(UserId);

        //封装成map
        Map<String, Object> Datamap = new HashMap<String, Object>();
        Datamap.put("Articlelist",Articlelist);
        Datamap.put("Friendlist",Friendlist);
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


    @RequestMapping("/UpdateInfo")
    //修改个人信息
    public String updateInfo(@RequestParam("DataMap") Map<String, Object> Datamap){
        BaseResponse response=new BaseResponse(StatusCode.Fail);
        int status = userService.updateUserInfo(Datamap);
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

    @RequestMapping("/sendPhoneCode")//绑定手机号或换绑手机号，发送验证码到手机
    public String sendPhoneCode(@RequestParam("UserId")int UserId,@RequestParam("PhoneNum")String PhoneNum){
        BaseResponse response=new BaseResponse(StatusCode.Fail);
        boolean status = userService.sendCode(UserId,PhoneNum);
        if (status){
            response.setCode(0);
            response.setMsg("成功");
            response.setData("验证码已发送");
            String result = JSON.toJSONString(response);
            return result;
        }
        else{
            response.setData("请求失败");
            String result = JSON.toJSONString(response);
            return  result;
        }
    }

    @RequestMapping("/checkPhoneCode")//用户收到短信后，对输入的验证码进行验证
    public String checkPhoneCode(@RequestParam("UserId")int UserId,@RequestParam("Code")int Code){
        BaseResponse response=new BaseResponse(StatusCode.Fail);
        PhoneCode phoneCode = userService.checkingCode(UserId,Code);
        if (phoneCode!=null){
            Map<String, Object> Datamap = new HashMap<String, Object>();
            Datamap.put("Phone",phoneCode.getPhone());
            Datamap.put("UserId",UserId);
            if(userService.updateUserPhone(Datamap)==2);
            response.setCode(0);
            response.setMsg("成功");
            response.setData("绑定手机号成功！");
            String result = JSON.toJSONString(response);
            return result;
        }
        else{
            response.setData("绑定手机号失败");
            String result = JSON.toJSONString(response);
            return  result;
        }
    }

    @RequestMapping("/updateNewScenery")//用户修改旅行目的地
    public String updateNewScenery(@RequestParam("UserId")int UserId,@RequestParam("NewScenery")String NewScenery){
        BaseResponse response=new BaseResponse(StatusCode.Fail);
        int status = userService.updateNewScenery(UserId, NewScenery);
        if (status!=0){
            response.setCode(0);
            response.setMsg("成功");
            response.setData("成功更新");
            String result = JSON.toJSONString(response);
            return result;
        }
        else{
            response.setData("更新失败");
            String result = JSON.toJSONString(response);
            return  result;
        }
    }


    @RequestMapping("/uploadPicture")//上传图片到服务器
    public String uploadPicture(HttpServletRequest request){
        BaseResponse response=new BaseResponse(StatusCode.Fail);
        MultipartHttpServletRequest req =(MultipartHttpServletRequest)request;
        MultipartFile multipartFile = req.getFile("file");//获取前端的upload的name参数"file"
        String imgname = Utils.putImage(multipartFile);//上传到服务器
        if (imgname!=null){
            userService.addPicture(imgname);
            response.setCode(0);
            response.setMsg("成功");
            response.setData(imgname);
            String result = JSON.toJSONString(response);
            return result;
        }
        String result = JSON.toJSONString(response);
        return result;
    }






    //查询全部用户
    public List<User> queryAllUser(){
        return userService.queryAllUser();
    }

    public List<User> queryUserByNameLike(String name){
        return userService.queryUserByNameLike(name);
    }

    public List<UserInfo> queryUserInfoByNameLike(String name){
        return userService.queryUserInfoByNameLike(name);
    }

    public int updateUserType(int id, String userType){
        return  userService.updateUserType(id, userType);
    }

}

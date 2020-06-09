package com.tyy.pojo.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    private int UserId;//用户ID
    private String Phone;//用户手机号
    private String Name;//用户名
    private String Sex;//用户性别
    private int Age;//用户年龄
    private String Photo;//用户头像
    private String Signature;//个性签名
    private String Home;//用户居住地
    private String NowScenery;//用户所在地
    private String NewScenery;//目标旅游地
    private Date RegisterTime;//注册时间
    private String UserType;//用户类别，C为学生，P为家长
    private int Good;//用户所有文章的总点赞量
    private int ArticleNum;//用户总发布的文章数
}

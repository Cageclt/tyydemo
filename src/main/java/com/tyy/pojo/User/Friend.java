package com.tyy.pojo.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Friend {
    private int UserId;//用户ID
    private int TargetId;//被添加为好友的用户ID
    private String Note;//对好友的备注
    private String Photo;//好友头像
    private String Signature;//好友个性签名
    private String NowScenery;//好友现在所处地
}

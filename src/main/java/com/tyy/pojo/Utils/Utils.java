package com.tyy.pojo.Utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.tyy.pojo.User.BillItem;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class Utils {
    //对两个list进行比对，取出不相同的元素封装成List返回
    public static List<BillItem> getDiffrent(List<BillItem> col1, List<BillItem> col2){
        //创建返回结果
        List<BillItem> diffrentResult = new ArrayList<>();
        //比较出两个集合的大小，在添加进map的时候先遍历较大集合，这样子可以减少没必要的判断
        List<BillItem> bigCol = null;
        List<BillItem> smallCol = null;
        if (col1.size() > col2.size()) {
            bigCol = col1;
            smallCol = col2;
        }else {
            bigCol = col2;
            smallCol = col1;
        }
        //创建 Map<对象,出现次数> (直接指定大小减少空间浪费)
        Map<Object, Integer> map = new HashMap<>(bigCol.size());
        //遍历大集合把元素put进map，初始出现次数为1
        for(BillItem p : bigCol) {
            map.put(p, 1);
        }
        //遍历小集合，如果map中不存在小集合中的元素，就添加到返回结果，如果存在，把出现次数置为2
        for(BillItem p : smallCol) {
            if (map.get(p) == null) {
                diffrentResult.add(p);
            }else {
                map.put(p, 2);
            }
        }
        //把出现次数为1的 Key:Value 捞出，并把Key添加到返回结果
        for(Map.Entry<Object, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 1) {
                diffrentResult.add((BillItem) entry.getKey());
            }
        }
        return diffrentResult;
    }


    /**
     * 对象转化为Map
     *
     * @param obj
     * @return
     * @throws Exception
     */
    public static Map<String, Object> objectToMap(Object obj) throws Exception {
        if (obj == null) {
            return null;
        }

        Map<String, Object> map = new HashMap<String, Object>();

        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            map.put(field.getName(), field.get(obj));
        }
        return map;
    }

    //基于阿里云的短信发送方法
    public static boolean sendSms(String phoneNum,String templateCode,Map<String,Object> code){

        //连接阿里云
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", " ", " ");
        IAcsClient client = new DefaultAcsClient(profile);

        //构建请求
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);//勿要改动
        request.setDomain("dysmsapi.aliyuncs.com");//勿要改动
        request.setVersion("2017-05-25");
        request.setAction("SendSms");//事件名称

        //自定义参数 手机号 验证码 签名 模板
        request.putQueryParameter("PhoneNumbers", phoneNum);
        request.putQueryParameter("SignName", "途游游");
        request.putQueryParameter("TemplateCode", templateCode);
        //短信验证码
        request.putQueryParameter("TemplateParam", JSON.toJSONString(code));
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            return response.getHttpResponse().isSuccess();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static String putImage(MultipartFile file){
        Date nowdate = new Date();
        SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        StringBuilder filename = new StringBuilder(sdf.format(nowdate));
        //生成时间防止文件名重复
        //生成一个随机数来防止文件名重复
        int x=(int)(Math.random()*1000);
        String str = String.valueOf(x);
            filename.append(str);

        //取得文件的格式后缀
        String originalLastName = file.getOriginalFilename();
        String picLastName = originalLastName.substring(originalLastName.lastIndexOf("."));
        filename.append(picLastName);

        //写死存储路径
        String realpath = "/home/img";
        try {
            File dir = new File(realpath);
            //若目录不存在则创建目录
            if (!dir.exists()) {
                dir.mkdir();
                System.out.println("创建文件目录成功：" + realpath);
            }
            File savefile = new File(realpath,filename.toString().toLowerCase());
            file.transferTo(savefile);
            System.out.println("添加图片成功！");
            return filename.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    public static String sendPost (String url, String json) throws ClientProtocolException, IOException{
        String charset = "utf-8";
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpClient client = HttpClients.createDefault();
        
        StringEntity entity = new StringEntity(json, charset);//解决中文乱码问题    
        entity.setContentEncoding(charset);    
        entity.setContentType("application/json");    
        httpPost.setEntity(entity);
        HttpResponse response = client.execute(httpPost);
        
        if(response.getStatusLine().getStatusCode() == 200){
            HttpEntity httpEntity = response.getEntity();
            return EntityUtils.toString(httpEntity, charset);
        }
        return null;
    }
    }







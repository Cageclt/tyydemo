import com.alibaba.fastjson.JSON;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.tyy.controller.BillController;
import com.tyy.controller.UserController;
import com.tyy.pojo.Article.Article;
import com.tyy.pojo.User.*;
import com.tyy.pojo.Utils.Utils;
import com.tyy.service.Bill.BillServiceImpl;
import com.tyy.service.User.UserService;
import com.tyy.service.User.UserServiceImpl;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.SimpleDateFormat;
import java.util.*;

public class UserTest {


    @Test
    // controller/UserService.queryUserInfoById
    public void queryUserInfoByIdtest() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserServiceImpl userServiceImpl = context.getBean("UserServiceImpl", UserServiceImpl.class);
        System.out.println(userServiceImpl.queryUserInfoById(1));
    }

    @Test
    // controller/UserController.queryAllUser
        public void queryAllUsertest() {
            ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
            UserController controller = context.getBean("UserController", UserController.class);
            for (User user : controller.queryAllUser()) {
                System.out.println(user);
            }
        }

        @Test
        // controller/UserController.queryUserByNameLike
        public void queryUserByNameLikeTest(){
            ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
            UserController controller = context.getBean("UserController", UserController.class);
            for (User user : controller.queryUserByNameLike("陈")) {
                System.out.println(user);
            }
        }

        @Test
        //  controller/UserController.queryUserInfoByNameLike
        public void queryUserInfoByNameLikeTest(){
            ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
            UserController controller = context.getBean("UserController", UserController.class);
            for (UserInfo userInfo : controller.queryUserInfoByNameLike("陈")) {
                System.out.println(userInfo);
            }
        }

        @Test
        //  service/userServiceImpl.updateUserType
        public void updateUserTypeTest(){
            ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
            UserServiceImpl userServiceImpl = context.getBean("UserServiceImpl", UserServiceImpl.class);
            System.out.println(userServiceImpl.updateUserType(1,"P"));
        }
        @Test
        //  service/userServiceImpl.addRelation
        public void addRelationTest(){
            ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
            UserServiceImpl userServiceImpl = context.getBean("UserServiceImpl", UserServiceImpl.class);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("UserId",1);
            map.put("TargetId",2);
            map.put("RelationShip","P");
            System.out.println(userServiceImpl.addRelation(map));
        }

        @Test
        //  service/userServiceImpl.updateUserInfo
        public void updateUserInfoTest(){
            ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
            UserServiceImpl userServiceImpl = context.getBean("UserServiceImpl", UserServiceImpl.class);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("UserId",2);
            map.put("Signature","测试触发器555");
            System.out.println(userServiceImpl.updateUserInfo(map));
        }
        @Test
        //  service/userServiceImpl.queryAllFriend
        public void queryAllFriendTest(){
            ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
            UserServiceImpl userServiceImpl = context.getBean("UserServiceImpl", UserServiceImpl.class);
            for (Friend friend : userServiceImpl.queryAllFriend(1)) {
                System.out.println(friend);
            }
    }

        @Test
        //  service/userServiceImpl.createRandomCode
        public void createRandomCodeTest(){
            ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
            UserServiceImpl userServiceImpl = context.getBean("UserServiceImpl", UserServiceImpl.class);
            PhoneCode phoneCode = new PhoneCode();
            phoneCode.setUserId(1);
            phoneCode.setPhone("22222222222");
            Date nowdate = new Date();
            SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sdf.format(nowdate);
            phoneCode.setTime(nowdate);


        }
        @Test
        //  service/userServiceImpl.checkingCode
        public void checkingCodeTest(){
            ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
            UserController userControllerImpl = context.getBean("UserController", UserController.class);
            String  result = userControllerImpl.checkPhoneCode(1, 556806);
            System.out.println(result);
        }

        @Test
        //  service/userServiceImpl.addFriend
        public void addFriendTest(){
            ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
            UserServiceImpl userServiceImpl = context.getBean("UserServiceImpl", UserServiceImpl.class);
            System.out.println(userServiceImpl.addFriend(1, 2));
        }

        @Test
        //  service/userServiceImpl.queryAllArticleById
        public void queryAllArticleByIdTest(){
            ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
            UserServiceImpl userServiceImpl = context.getBean("UserServiceImpl", UserServiceImpl.class);
            for (Article article : userServiceImpl.queryAllArticleById(1)) {
                System.out.println(article);
            }
        }

        @Test
        //  service/userServiceImpl.queryAllBillById
        public void queryAllBillByIdTest(){
            ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
            BillServiceImpl billServiceImpl = context.getBean("BillServiceImpl", BillServiceImpl.class);
            List<Bill> bills = billServiceImpl.queryAllBillById(1);
            for (Bill bill : bills) {
                System.out.println(bill);
            }
        }

        @Test
        //  service/userServiceImpl.queryAllBillItemById
        public void queryAllBillItemByIdTest(){
            ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
            BillServiceImpl billServiceImpl = context.getBean("BillServiceImpl", BillServiceImpl.class);
            List<BillItem> billItems = billServiceImpl.queryAllBillItemById(1);
            for (BillItem billItem : billItems) {
                System.out.println(billItem);
            }
        }
        @Test
        //  service/userServiceImpl.addNewBillItem
        public void addNewBillItemTest(){
            ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
            BillServiceImpl billServiceImpl = context.getBean("BillServiceImpl", BillServiceImpl.class);
            BillItem billItem = new BillItem();
            billItem.setBillId(2);
            billItem.setItemType("交通");
            billItem.setCost(3);
            billItem.setInfo("坐地铁花了点钱");
            Date nowdate = new Date();
            SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sdf.format(nowdate);
            billItem.setCostTime(nowdate);
            System.out.println(billServiceImpl.addNewBillItem(billItem));
        }

        @Test
        //  service/userServiceImpl.deleteBillItemById
        public void deleteBillItemByIdTest(){
            ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
            BillServiceImpl billServiceImpl = context.getBean("BillServiceImpl", BillServiceImpl.class);
            System.out.println(billServiceImpl.deleteBillItemById(2));
        }

        @Test
        //  service/userServiceImpl.updateBillItem
        public void updateBillItemTest(){
            ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
            BillServiceImpl billServiceImpl = context.getBean("BillServiceImpl", BillServiceImpl.class);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("BillId",2);
            map.put("Cost",6);
            map.put("Info","坐地铁到市民中心2");
//            System.out.println(billServiceImpl.updateBillItem(map));
        }


        @Test
        //  service/userServiceImpl.updateNewScenery
        public void updateNewSceneryTest(){
            ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
            UserServiceImpl userServiceImpl = context.getBean("UserServiceImpl", UserServiceImpl.class);
            System.out.println(userServiceImpl.updateNewScenery(1, "长沙"));
        }


    @Test
    //  service/userServiceImpl.queryAllArticleCollectById
    public void queryAllArticleCollectByIdTest(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserServiceImpl userServiceImpl = context.getBean("UserServiceImpl", UserServiceImpl.class);
        for (Article article : userServiceImpl.queryAllArticleCollectById(1)) {
            System.out.println(article);
        }
    }

    @Test
    // service/userServiceImpl.queryIsChoicelyCount
    public void queryIsChoicelyCountTest(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserServiceImpl userServiceImpl = context.getBean("UserServiceImpl", UserServiceImpl.class);

    }

    @Test
    // Utils/Utils.getDiffrent
    public void getDiffrentTest(){
        List<BillItem> list1 = new ArrayList<>();
        List<BillItem> list2 = new ArrayList<>();
        BillItem item1 = new BillItem(1,1,"交通",2,"坐地铁",new Date());
        BillItem item2 = new BillItem(1,1,"交通",2,"坐地铁",new Date());
        BillItem item3 = new BillItem(2,2,"吃饭",5,"好吃",new Date());
        list1.add(item1);
        list2.add(item2);
        list2.add(item3);
        List<BillItem> Diffrent = new ArrayList<>();
        Diffrent = Utils.getDiffrent(list1,list2);
        System.out.println(Diffrent);
    }

    @Test
    //阿里云短信服务测试
    public void sendTest(){

        //连接阿里云
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4FyJqPbDcQG9Qu3Us4bA", "pPY3xldRMUuWiB2ijedV0y94eubApp");
        IAcsClient client = new DefaultAcsClient(profile);

        //构建请求
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);//勿要改动
        request.setDomain("dysmsapi.aliyuncs.com");//勿要改动
        request.setVersion("2017-05-25");
        request.setAction("SendSms");//事件名称

        //自定义参数 手机号 验证码 签名 模板
        request.putQueryParameter("PhoneNumbers", "13751577034");
        request.putQueryParameter("SignName", "途游游");
        request.putQueryParameter("TemplateCode", "SMS_189836374");

        //短信验证码
        HashMap<String, Object> map = new HashMap<>();
        map.put("code",1555);
        request.putQueryParameter("TemplateParam", JSON.toJSONString(map));
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void sendCodeTest(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserServiceImpl userServiceImpl = context.getBean("UserServiceImpl", UserServiceImpl.class);
        userServiceImpl.sendCode(1,"18038542784");
    }

    @Test
    public void updateBillTest() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        BillController billController= context.getBean("BillController", BillController.class);
        List<BillItem> list = new ArrayList<BillItem>();

//        Date nowdate = new Date();
//        SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        //生成当前时间
//
//        Date date1 = new Date(2020, 04, 17, 17, 41, 04);
//        Date date2 = new Date(2020, 04, 17, 18, 04, 17);
//        sdf.format(date1);
//        sdf.format(date2);

        BillItem item1 = new BillItem(1, "住宿", 200, "插入新增",new Date());//增
        BillItem item2 = new BillItem(18,1, "用餐", 8, "在饭店吃了顿",
                new Date(2020,06,13,00,28,04));//不变
        BillItem item3 = new BillItem(19,1, "交通", 6, "测试坐地铁市民中心");//改
        list.add(item1);
        list.add(item2);
        list.add(item3);
        System.out.println(billController.updateBillItem(1, list));
    }

    @Test
    public void TestLastName(){
        String string = "https://i0.hdslb.com/bfs/archive/4de86ebf90b044bf9ba2becf042a8977062b3f99.png";
        String result = string.substring(string.lastIndexOf("."));
        System.out.println(result);
    }


    @Test
    //  service/userServiceImpl.addPicture
    public void addPictureTest(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserServiceImpl userServiceImpl = context.getBean("UserServiceImpl", UserServiceImpl.class);
        System.out.println(userServiceImpl.addPicture("4de86ebf90b044bf9ba2becf042a8977062b3f99.png"));
    }





}


package com.tyy.controller;

import com.alibaba.fastjson.JSON;
import com.tyy.pojo.User.Bill;
import com.tyy.pojo.User.BillItem;
import com.tyy.pojo.Utils.BaseResponse;
import com.tyy.pojo.Utils.StatusCode;
import com.tyy.pojo.Utils.Utils;
import com.tyy.service.Bill.BillService;
import com.tyy.service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@RestController("BillController")
public class BillController {
    @Autowired
    private BillService billService;

    public void setBillService(BillService billService) {
        this.billService = billService;
    }

    @RequestMapping("/Bill")//进入账单主界面
    public String billInitialize(@RequestParam("UserId")int UserId){
        BaseResponse response=new BaseResponse(StatusCode.Fail);
        List<Bill> Billlist = new ArrayList<Bill>();
        Billlist = billService.queryAllBillById(UserId);
        if(Billlist != null){
            response.setCode(0);
            response.setMsg("成功");
            response.setData(Billlist);
            String result = JSON.toJSONString(response);
            return result;
        }
        else{
            String result = JSON.toJSONString(response);
            return  result;
        }
    }

    @RequestMapping("/addNewBill")//添加新的账单
    public String addNewBill(@RequestParam("UserId")int UserId,@RequestParam("Title")String Title){
        BaseResponse response=new BaseResponse(StatusCode.Fail);
        int status = billService.addNewBill(UserId, Title);
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

    @RequestMapping("/viewBill")//查看账单详情
    public String viewBill(@RequestParam("BillId")int BillId){
        BaseResponse response=new BaseResponse(StatusCode.Fail);
        List<BillItem> BillItemlist = new ArrayList<BillItem>();
        BillItemlist = billService.queryAllBillItemById(BillId);
        if(BillItemlist != null && !BillItemlist.isEmpty()){
            response.setCode(0);
            response.setMsg("成功");
            response.setData(BillItemlist);
            String result = JSON.toJSONString(response);
            return result;
        }
        else{
            String result = JSON.toJSONString(response);
            return  result;
        }
    }

    @RequestMapping("/updateBillItem")//更新账单详情
    public String updateBillItem(@RequestParam("BillId")int BillId,@RequestParam("BillItemlist") List<BillItem> newList) throws Exception {
        BaseResponse response=new BaseResponse(StatusCode.Fail);
        List<BillItem> oldList = new ArrayList<BillItem>();
        oldList = billService.queryAllBillItemById(BillId);//根据账单ID查出所有的账单项
        int delete = 0;
        int add = 0;
        int update = 0;
        Iterator<BillItem> oldIterator = oldList.iterator();
        while(oldIterator.hasNext()){
            BillItem old = oldIterator.next();
            int exist = 0;//是否依然存在新的list中

            Iterator<BillItem> newIterator = newList.iterator();
            while (newIterator.hasNext()){
                BillItem New = newIterator.next();
                if(old.getId().equals(New.getId()))
                {
                    exist = exist+1;
                    break;
                }
            }

            if(exist==0){//已经不存在
                oldList.remove(old);//将其从账单中删除
                delete = delete + billService.deleteBillItemById(old.getId().intValue());//删除该记录
                oldIterator.remove();
            }
        }


        List<BillItem> diffrentList = new ArrayList<BillItem>();
        diffrentList = Utils.getDiffrent(oldList,newList);//抓取出所有修改过的数据

        for (BillItem diffrentitem : diffrentList) {
            if (diffrentitem.getId()==null){
                add = add + billService.addNewBillItem(diffrentitem);//添加新的账单记录
            }
            else update = update + billService.updateBillItem(Utils.objectToMap(diffrentitem));//修改账单记录

        }
        double Sum = billService.queryBillSum(BillId);//查出新的账单中的Sum
        HashMap<String, Object> map = new HashMap<>();
        map.put("Sum",Sum);
        map.put("BillId",BillId);
        billService.updateBillSum(map);//修改账单总值Sum
        int status = add+delete+update;
        if (status != 0){
            response.setCode(0);
            response.setMsg("成功执行");
            String msgresult;
            msgresult = "删除"+delete+"条数据，"+"添加"+add+"条数据，"+"修改"+update+"条数据";
            response.setData(msgresult);
            String result = JSON.toJSONString(response);
            return result;
        }
        else
            response.setCode(0);
        response.setMsg("成功执行");
        String msgresult;
        msgresult = "没有修改任何数据";
        response.setData(msgresult);
        String result = JSON.toJSONString(response);
        return result;
    }
}

package com.tyy.service.Bill;

import com.tyy.pojo.User.Bill;
import com.tyy.pojo.User.BillItem;

import java.util.List;
import java.util.Map;

public interface BillService {
    //查看用户所有的账单
    List<Bill> queryAllBillById(int id);

    //创建新的账单
    int addNewBill(int UserId,String Title);

    //查看账单详情
    List<BillItem> queryAllBillItemById(int id);

    //添加新的消费记录
    int addNewBillItem(BillItem billItem);

    //更新消费记录
    int updateBillItem(Map<String,Object> map);

    //删除某账单记录
    int deleteBillItemById(int id);

    //查询账单的总消费
    double queryBillSum(int Billid);

    //修改账单的总消费
    int updateBillSum(Map <String,Object> map);


}

package com.tyy.dao.Bill;

import com.tyy.pojo.User.Bill;
import com.tyy.pojo.User.BillItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BillMapper {
    //查看用户所有的账单
    List<Bill> queryAllBillById(@Param("UserId")int id);

    //创建新的账单
    int addNewBill(Map<String,Object> map);

    //查看账单详情
    List<BillItem> queryAllBillItemById(@Param("BillId")int id);

    //删除消费记录
    int deleteBillItemById(@Param("Id")int id);

    //添加新的消费记录
    int addNewBillItem(BillItem billItem);

    //更新消费记录
    int updateBillItem(Map <String,Object> map);

    //修改账单的总消费
    int updateBillSum(Map <String,Object> map);

    //查询账单的总消费
    double queryBillSum(@Param("BillId")int id);
}

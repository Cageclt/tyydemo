package com.tyy.service.Bill;

import com.tyy.dao.Bill.BillMapper;
import com.tyy.dao.User.UserMapper;
import com.tyy.pojo.User.Bill;
import com.tyy.pojo.User.BillItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("BillServiceImpl")
public class BillServiceImpl implements BillService{
    @Autowired
    private BillMapper billMapper;

    public void setBillMapper(BillMapper billMapper) {
        this.billMapper = billMapper;
    }

    //查看用户所有的账单
    public List<Bill> queryAllBillById(int id) {
        return billMapper.queryAllBillById(id);
    }

    public int addNewBill(int UserId,String Title) {
        Map<String, Object> map = new HashMap<String, Object>();
        Date nowdate = new Date();
        SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.format(nowdate);
        map.put("UserId",UserId);
        map.put("Title",Title);
        map.put("Time",nowdate);
        map.put("Sum",0);
        return billMapper.addNewBill(map);
    }

    public List<BillItem> queryAllBillItemById(int id) {
        return billMapper.queryAllBillItemById(id);
    }

    public int deleteBillItemById(int id) {
        return billMapper.deleteBillItemById(id);
    }

    @Override
    public double queryBillSum(int Billid) {
        return billMapper.queryBillSum(Billid);
    }

    @Override
    public int updateBillSum(Map<String, Object> map) {
        return billMapper.updateBillSum(map);
    }

    public int addNewBillItem(BillItem billItem) {
        return billMapper.addNewBillItem(billItem);
    }

    public int updateBillItem(Map<String, Object> map) {
        return billMapper.updateBillItem(map);
    }

}

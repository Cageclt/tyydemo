package com.tyy.pojo.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillItem {
    private Integer Id;
    private int BillId;
    private String ItemType;
    private double Cost;
    private String Info;
    private Date CostTime;

    public BillItem(int billId, String itemType, double cost, String info, Date costTime) {
        BillId = billId;
        ItemType = itemType;
        Cost = cost;
        Info = info;
        CostTime = costTime;
    }

    public BillItem(int billId, String itemType, double cost, String info) {
        BillId = billId;
        ItemType = itemType;
        Cost = cost;
        Info = info;
    }

    public BillItem(Integer id,int billId, String itemType, double cost, String info) {
        Id = id;
        BillId = billId;
        ItemType = itemType;
        Cost = cost;
        Info = info;

    }



}

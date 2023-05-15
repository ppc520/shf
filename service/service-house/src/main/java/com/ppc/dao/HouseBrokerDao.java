package com.ppc.dao;

import com.ppc.entity.HouseBroker;

import java.util.List;

public interface HouseBrokerDao extends BaseDao<HouseBroker>{
    //根据房源id查询经纪人
    List<HouseBroker> getHouseBrokersByHouseId(Long HouseId);
}

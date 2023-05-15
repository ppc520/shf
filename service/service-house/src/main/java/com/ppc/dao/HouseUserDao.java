package com.ppc.dao;

import com.ppc.entity.HouseUser;

import java.util.List;

public interface HouseUserDao extends BaseDao<HouseUser>{
    //根据房源id查房东信息
    List<HouseUser> getHouseUserByHouseId(Long houseId);
}

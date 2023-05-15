package com.ppc.service;

import com.ppc.entity.HouseUser;

import java.util.List;

public interface HouseUserService extends BaseService<HouseUser>{
    //根据房源id查房东信息
    List<HouseUser> getHouseUserByHouseId(Long houseId);
}

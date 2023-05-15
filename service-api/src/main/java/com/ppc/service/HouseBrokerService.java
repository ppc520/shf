package com.ppc.service;

import com.ppc.entity.HouseBroker;

import java.util.List;

public interface HouseBrokerService extends BaseService<HouseBroker>{
    List<HouseBroker> getHouseBrokersByHouseId(Long houseId);

}

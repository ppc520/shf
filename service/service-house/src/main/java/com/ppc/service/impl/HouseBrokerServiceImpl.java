package com.ppc.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ppc.dao.BaseDao;
import com.ppc.dao.HouseBrokerDao;
import com.ppc.entity.HouseBroker;
import com.ppc.service.HouseBrokerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = HouseBrokerService.class)
@Transactional
public class HouseBrokerServiceImpl extends BaseServiceImpl<HouseBroker> implements HouseBrokerService {
    @Autowired
    private HouseBrokerDao houseBrokerDao;

    @Override
    protected BaseDao<HouseBroker> getEntityDao() {
        return houseBrokerDao;
    }

    @Override
    public List<HouseBroker> getHouseBrokersByHouseId(Long houseId) {
        return houseBrokerDao.getHouseBrokersByHouseId(houseId);
    }
}

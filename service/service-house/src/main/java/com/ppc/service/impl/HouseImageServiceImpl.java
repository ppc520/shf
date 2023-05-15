package com.ppc.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ppc.dao.BaseDao;
import com.ppc.dao.HouseImageDao;
import com.ppc.entity.HouseImage;
import com.ppc.service.HouseImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = HouseImageService.class)
@Transactional
public class HouseImageServiceImpl extends BaseServiceImpl<HouseImage> implements HouseImageService {
   @Autowired
   private HouseImageDao houseImageDao;
    @Override
    protected BaseDao<HouseImage> getEntityDao() {
        return houseImageDao;
    }

    @Override
    public List<HouseImage> getHouseImagesByHouseIdAndType(Long houseId, Integer type) {
        return houseImageDao.getHouseImagesByHouseIdAndType(houseId,type);
    }
}

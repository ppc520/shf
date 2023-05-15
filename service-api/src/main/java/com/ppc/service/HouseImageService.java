package com.ppc.service;

import com.ppc.entity.HouseImage;

import java.util.List;

public interface HouseImageService extends BaseService<HouseImage>{
    List<HouseImage> getHouseImagesByHouseIdAndType(Long houseId,Integer type);

}

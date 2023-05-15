package com.ppc.service;

import com.github.pagehelper.PageInfo;
import com.ppc.entity.House;
import com.ppc.vo.HouseQueryVo;
import com.ppc.vo.HouseVo;

public interface HouseService extends BaseService<House>{
    void publish(Long id, Integer status);

    //调用houseService前端分页及带条件查询的方法
    PageInfo<HouseVo> findPageList(Integer pageNum, Integer pageSize, HouseQueryVo houseQueryVo);
}

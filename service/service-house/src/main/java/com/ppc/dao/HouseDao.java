package com.ppc.dao;

import com.github.pagehelper.Page;
import com.ppc.entity.House;
import com.ppc.vo.HouseQueryVo;
import com.ppc.vo.HouseVo;

public interface HouseDao extends BaseDao<House>{

    //调用houseService前端分页及带条件查询的方法
    Page<HouseVo> findPageList(HouseQueryVo houseQueryVo);
}

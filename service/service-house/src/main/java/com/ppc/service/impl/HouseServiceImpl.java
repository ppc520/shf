package com.ppc.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ppc.dao.BaseDao;
import com.ppc.dao.DictDao;
import com.ppc.dao.HouseDao;
import com.ppc.entity.House;
import com.ppc.service.HouseService;
import com.ppc.vo.HouseQueryVo;
import com.ppc.vo.HouseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@Service(interfaceClass = HouseService.class)
@Transactional

public class HouseServiceImpl extends BaseServiceImpl<House> implements HouseService {
    @Autowired
    private HouseDao houseDao;
    @Autowired
    private DictDao dictDao;
    @Override
    protected BaseDao<House> getEntityDao() {
        return houseDao;
    }

    @Override
    public void publish(Long id, Integer status) {
        House house=new House();
        house.setId(id);
        house.setStatus(status);
        houseDao.update(house);
    }
    @Override
    public PageInfo<HouseVo> findPageList(Integer pageNum, Integer pageSize, HouseQueryVo houseQueryVo) {
        //开启分页
        PageHelper.startPage(pageNum,pageSize);
        //调用houseService前端分页及带条件查询的方法
        Page<HouseVo> pages=houseDao.findPageList(houseQueryVo);
        for (HouseVo houseVo : pages) {
            houseVo.setHouseTypeName(dictDao.getNameById(houseVo.getHouseTypeId()));
            houseVo.setFloorName(dictDao.getNameById(houseVo.getFloorId()));
            houseVo.setDirectionName(dictDao.getNameById(houseVo.getDirectionId()));
        }
        return new PageInfo<>(pages,5);
    }

    @Override
    public House getById(Serializable id) {
        House house = houseDao.getById(id);
        //获取户型
        house.setHouseTypeName(dictDao.getNameById(house.getHouseTypeId()));
        //获取楼层
        house.setFloorName(dictDao.getNameById(house.getFloorId()));
//        获取朝向
        house.setDirectionName(dictDao.getNameById(house.getDirectionId()));
        //获取建筑结构
        house.setBuildStructureName(dictDao.getNameById(house.getBuildStructureId()));
        //获取装修情况
        house.setDecorationName(dictDao.getNameById(house.getDecorationId()));
        //获取房屋用途
        house.setHouseUseName(dictDao.getNameById(house.getHouseUseId()));
        return house;
    }


}

package com.ppc.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.ppc.entity.Community;
import com.ppc.entity.House;
import com.ppc.entity.HouseBroker;
import com.ppc.entity.HouseImage;
import com.ppc.result.Result;
import com.ppc.service.CommunityService;
import com.ppc.service.HouseBrokerService;
import com.ppc.service.HouseImageService;
import com.ppc.service.HouseService;
import com.ppc.vo.HouseQueryVo;
import com.ppc.vo.HouseVo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/house")
public class HouseController {
    @Reference
    private HouseService houseService;
    @Reference
    private CommunityService communityService;
    @Reference
    private HouseImageService houseImageService;
    @Reference
    private HouseBrokerService houseBrokerService;

    @RequestMapping("/list/{pageNum}/{pageSize}")
    public Result findPageList(@PathVariable("pageNum") Integer pageNum, @PathVariable("pageSize") Integer pageSize,
                               @RequestBody HouseQueryVo houseQueryVo){
        //调用houseService前端分页及带条件查询的方法
        PageInfo<HouseVo> pageInfo=houseService.findPageList(pageNum,pageSize,houseQueryVo);
        return Result.ok(pageInfo);
    }

    @RequestMapping("/info/{houseId}")
    private Result info(@PathVariable Long houseId){
        House house = houseService.getById(houseId);
        Community community = communityService.getById(house.getCommunityId());
        List<HouseImage> houseImage1List = houseImageService.getHouseImagesByHouseIdAndType(houseId, 1);
        List<HouseBroker> houseBrokerList = houseBrokerService.getHouseBrokersByHouseId(houseId);
        Map map=new HashMap();
        map.put("house",house);
        map.put("community",community);
        map.put("houseImage1List",houseImage1List);
        map.put("houseBrokerList",houseBrokerList);
        //设置默认没有关注房源
        map.put("isFollow",false);
        return Result.ok(map);
    }
}

package com.ppc.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.ppc.entity.*;
import com.ppc.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/house")
public class HouseController extends BaseController {
    @Reference
    private HouseService houseService;
    @Reference
    private CommunityService communityService;
    @Reference
    private DictService dictService;
    @Reference
    private HouseImageService houseImageService;
    @Reference
    private HouseBrokerService houseBrokerService;
    @Reference
    private HouseUserService houseUserService;


    @RequestMapping
    public String index(Map map, HttpServletRequest request) {
        Map<String, Object> filters = getFilters(request);

        map.put("filters", filters);

        PageInfo<House> page = houseService.findPage(filters);

        map.put("page", page);
        setRequestAttribute(map);


        return "house/index";
    }

    @RequestMapping("/create")
    public String goAddPage(Map map){
        setRequestAttribute(map);


        return "house/create";
    }

    @RequestMapping("/save")
    public String save(House house){
        houseService.insert(house);
        return "common/successPage";
    }

    @RequestMapping("/edit/{id}")
    public String goEditPage(@PathVariable Long id,Map map){
        House house=houseService.getById(id);
        map.put("house",house);

        setRequestAttribute(map);

        return "house/edit";
    }

    @RequestMapping("/update")
    public String update(House house){
        houseService.update(house);
        return "common/successPage";
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        houseService.delete(id);
        return "redirect:/house";
    }

    @RequestMapping("/publish/{id}/{status}")
    public String publish(@PathVariable("id") Long id,@PathVariable("status") Integer status){
        houseService.publish(id,status);
        return "redirect:/house";
    }

    //查询房源详情
    @RequestMapping("/{id}")
    public String show(@PathVariable Long id,Map map){
        House house = houseService.getById(id);
        map.put("house",house);

        Community community = communityService.getById(house.getCommunityId());
        map.put("community",community);
        //查询房源图片
        List<HouseImage> houseImages1List = houseImageService.getHouseImagesByHouseIdAndType(id, 1);
        //查询房产图片
        List<HouseImage> houseImages2List = houseImageService.getHouseImagesByHouseIdAndType(id, 2);
        //查询经纪人
        List<HouseBroker> houseBrokerList = houseBrokerService.getHouseBrokersByHouseId(id);
        //查询房东
        List<HouseUser> houseUserList = houseUserService.getHouseUserByHouseId(id);

        map.put("houseImage1List",houseImages1List);
        map.put("houseImage2List",houseImages2List);
        map.put("houseUserList",houseUserList);
        map.put("houseBrokerList",houseBrokerList);

        return "house/show";
    }




    //将所有小区及数据字典中的数据放入request域中
    public void setRequestAttribute(Map map){
        //获取所有小区
        List<Community> communityList = communityService.findAll();
        //获取所有户型
        List<Dict> houseTypeList = dictService.findListByDictCode("houseType");
        //获取楼层
        List<Dict> floorList = dictService.findListByDictCode("floor");
        //获取建筑结构
        List<Dict> buildStructureList = dictService.findListByDictCode("buildStructure");
        //获取朝向
        List<Dict> directionList = dictService.findListByDictCode("direction");
        //获取装修情况
        List<Dict> decorationList = dictService.findListByDictCode("decoration");
        //获取房屋用途
        List<Dict> houseUseList = dictService.findListByDictCode("houseUse");

        map.put("communityList",communityList);
        map.put("houseTypeList",houseTypeList);
        map.put("floorList",floorList);
        map.put("buildStructureList",buildStructureList);
        map.put("directionList",directionList);
        map.put("decorationList",decorationList);
        map.put("houseUseList",houseUseList);

    }

}

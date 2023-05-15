package com.ppc.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ppc.entity.Admin;
import com.ppc.entity.HouseBroker;
import com.ppc.service.AdminService;
import com.ppc.service.HouseBrokerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/houseBroker")
public class HouseBrokerController {
    @Reference
    private AdminService adminService;
    @Reference
    private HouseBrokerService houseBrokerService;

    @RequestMapping("/create")
    public String goAddPage(@RequestParam("houseId") Long houseId, Map map) {

        List<Admin> adminList = adminService.findAll();
        map.put("adminList", adminList);
        map.put("houseId", houseId);
        return "houseBroker/create";
    }

    @RequestMapping("/save")
    public String save(HouseBroker houseBroker) {
        Admin admin = adminService.getById(houseBroker.getBrokerId());

        houseBroker.setBrokerHeadUrl(admin.getHeadUrl());
        houseBroker.setBrokerName(admin.getName());

        houseBrokerService.insert(houseBroker);
        return "common/successPage";
    }

    @RequestMapping("/edit/{id}")
    public String goEditPage(@PathVariable Long id, Map map) {
        HouseBroker broker = houseBrokerService.getById(id);
        map.put("houseBroker", broker);
        List<Admin> adminList = adminService.findAll();
        map.put("adminList", adminList);
        return "houseBroker/edit";
    }

    @RequestMapping("/update")
    public String update(HouseBroker houseBroker){
        Admin admin = adminService.getById(houseBroker.getBrokerId());
        houseBroker.setBrokerHeadUrl(admin.getHeadUrl());
        houseBroker.setBrokerName(admin.getName());

        houseBrokerService.update(houseBroker);
        return "common/successPage";
    }

    @RequestMapping("delete/{houseId}/{brokerId}")
    public String delete(@PathVariable("houseId") Long houseId,@PathVariable("brokerId") Long brokerId){
        houseBrokerService.delete(brokerId);
        return "redirect:/house/"+houseId;
    }



}

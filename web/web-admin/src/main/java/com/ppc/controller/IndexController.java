package com.ppc.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ppc.entity.Admin;
import com.ppc.entity.Permission;
import com.ppc.service.AdminService;
import com.ppc.service.PermissionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class IndexController {
    @Reference
    private AdminService adminService;
    @Reference
    private PermissionService permissionService;
    @RequestMapping("/")
    public String index(Map map){
        Long userId=1L;
        Admin admin = adminService.getById(userId);
        List<Permission> permissionList=permissionService.getMenuPermissionByAdminId(admin.getId());
        map.put("admin",admin);
        map.put("permissionList",permissionList);
        return "frame/index";
    }
    @RequestMapping("/main")
    public String main(){
        return "frame/main";
    }
}

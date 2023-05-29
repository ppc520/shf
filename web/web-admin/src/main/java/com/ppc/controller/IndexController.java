package com.ppc.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ppc.entity.Admin;
import com.ppc.entity.Permission;
import com.ppc.service.AdminService;
import com.ppc.service.PermissionService;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
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
//        Long userId=1L;
//        Admin admin = adminService.getById(userId);
        //通过SpringSecurity获取User对象，这个User时SpringSecurityCore里面的类
        User user= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //获取admin对象，以获取admin的Id
        Admin admin = adminService.getAdminByUsername(user.getUsername());
        List<Permission> permissionList=permissionService.getMenuPermissionByAdminId(admin.getId());
        map.put("admin",admin);
        map.put("permissionList",permissionList);
        return "frame/index";
    }
    @RequestMapping("/main")
    public String main(){
        return "frame/main";
    }

    @RequestMapping("/login")
    public String goLoginPage(){
        return "frame/login";
    }

    @RequestMapping("/auth")
    public String auth(){
        return "frame/auth";
    }
}

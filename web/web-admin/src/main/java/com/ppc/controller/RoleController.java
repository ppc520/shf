package com.ppc.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.ppc.entity.Role;
import com.ppc.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Controller
@RequestMapping("/role")
public class RoleController extends BaseController{
    public static final String SUCCESS_PAGE = "common/successPage";
    @Reference
    private RoleService roleService;

//    @RequestMapping
//    public String index(Map map) {
//        List<Role> roleList = roleService.findAll();
//        //将map放入request域
//        map.put("list", roleList);
//        return "role/index";
//    }

//    分页及带条件查询
    @RequestMapping
    public String index(Map map,HttpServletRequest request){
        Map<String,Object> filters=getFilters(request);
        map.put("filters",filters);
        PageInfo<Role> pageInfo=roleService.findPage(filters);
        map.put("page",pageInfo);
        return "role/index";
    }

    @RequestMapping("/create")
    public String goAddPage() {
        return "role/create";
    }

    @RequestMapping("/save")
    public String save(Role role) {
        Integer insert = roleService.insert(role);
//        insert>0? :return SUCCESS_PAGE;
        return SUCCESS_PAGE;
    }

    @RequestMapping("/delete/{roleId}")
    public String delete(@PathVariable Long roleId) {
        roleService.delete(roleId);
        return "redirect:/role";
    }

    @RequestMapping("/edit/{roleId}")
    public String goEditPage(@PathVariable Long roleId, Map map) {
        Role role = roleService.getById(roleId);
//        将查询到的角色放入request域中
        map.put("role",role);
        return "role/edit";
    }

    @RequestMapping("/update")
    public String update(Role role){
        roleService.update(role);
        return SUCCESS_PAGE;
    }


}

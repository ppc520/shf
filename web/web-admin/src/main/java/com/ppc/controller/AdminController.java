package com.ppc.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.ppc.entity.Admin;
import com.ppc.service.AdminService;
import com.ppc.util.QiniuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController{
    @Reference
    private AdminService adminService;

//    分页及带条件查询
    @RequestMapping
    public String findPage(ModelMap map, HttpServletRequest request){
        Map<String,Object> filters=getFilters(request);
        map.put("filters",filters);
        PageInfo<Admin> pageInfo=adminService.findPage(filters);
        map.put("page",pageInfo);
        return "admin/index";
    }

    @RequestMapping("/create")
    public String goAddPage(){
        return "admin/create";
    }

    @RequestMapping("/save")
    public String save(Admin admin){
        adminService.insert(admin);
        return "common/successPage";
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        adminService.delete(id);
        return "redirect:/admin";
    }

    @RequestMapping("/edit/{id}")
    public String goEditPage(@PathVariable Long id,Map map){
        Admin admin = adminService.getById(id);
        map.put("admin",admin);
        return "admin/edit";
    }

    @RequestMapping("/update")
    public String update(Admin admin){
        adminService.update(admin);
        return "common/successPage";
    }

    @RequestMapping("/uploadShow/{id}")
    public String toUploadPage(@PathVariable Long id,Map map){
        map.put("id",id);
        return "admin/upload";
    }

    @RequestMapping("/upload/{id}")
    public String upload(@PathVariable Long id, @RequestParam MultipartFile file){
        try {
            Admin admin = adminService.getById(id);
            byte[] bytes = file.getBytes();
            String uuidName= UUID.randomUUID().toString();
            QiniuUtil.upload2Qiniu(bytes,uuidName);

            admin.setHeadUrl("http://ru6fapo6u.hn-bkt.clouddn.com/"+uuidName);
            adminService.update(admin);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "common/successPage";

    }

}

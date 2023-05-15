package com.ppc.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.ppc.entity.Community;
import com.ppc.entity.Dict;
import com.ppc.service.CommunityService;
import com.ppc.service.DictService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/community")
public class CommunityController extends BaseController{

    @Reference
    private CommunityService communityService;
    @Reference
    private DictService dictService;

    @RequestMapping
    public String index(Map map, HttpServletRequest request){
        Map<String, Object> filters = getFilters(request);

        map.put("filters",filters);

        PageInfo<Community> pageInfo = communityService.findPage(filters);

        map.put("page",pageInfo);

        List<Dict> areaList = dictService.findListByDictCode("beijing");

        map.put("areaList",areaList);

        return "community/index";
    }

    @RequestMapping("/create")
    public String goAddPage(Map map){
        List<Dict> areaList = dictService.findListByDictCode("beijing");

        map.put("areaList",areaList);

        return "community/create";
    }

    @RequestMapping("/save")
    public String save(Community community){
        communityService.insert(community);

        return "common/successPage";
    }

    @RequestMapping("/edit/{id}")
    public String goEditPage(@PathVariable Long id,Map map){
        List<Dict> areaList = dictService.findListByDictCode("beijing");

        map.put("areaList",areaList);

        Community community = communityService.getById(id);

        map.put("community",community);

        return "community/edit";
    }

    @RequestMapping("/update")
    public String update(Community community){
        communityService.update(community);
        return "common/successPage";
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable Long id,Map map){
        communityService.delete(id);
        return "redirect:/community";
    }
}

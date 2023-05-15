package com.ppc.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ppc.entity.Dict;
import com.ppc.result.Result;
import com.ppc.service.DictService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/dict")
public class DictController {
    @Reference
    DictService dictService;

    @RequestMapping
    public String index() {
        return "dict/index";
    }

    @ResponseBody
    @RequestMapping("/findZnodes")
    public Result findZnodes(@RequestParam(value = "id",defaultValue = "0") Long id){
        List<Map<String,Object>> zNodes=dictService.findZnodes(id);
        return Result.ok(zNodes);
    }

    @ResponseBody
    @RequestMapping("/findListByParentId/{areaId}")
    public Result findlistByParentId(@PathVariable("areaId") Long areaId){
        List<Dict> list = dictService.findListByParentId(areaId);
        return Result.ok(list);
    }

}

package com.ppc.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ppc.entity.Dict;
import com.ppc.result.Result;
import com.ppc.service.DictService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("dict")
public class DictController {
    @Reference
    DictService dictService;

    @RequestMapping("/findListByDictCode/{dictCode}")
    public Result findListByDictCode(@PathVariable("dictCode") String dictCode){
        List<Dict> dictList = dictService.findListByDictCode(dictCode);
        return Result.ok(dictList);
    }
    //查询所有子节点
    @RequestMapping("/findListByParentId/{areaId}")
    public Result findListByParentId(@PathVariable Long areaId){
        List<Dict> listByParentId = dictService.findListByParentId(areaId);
        return Result.ok(listByParentId);
    }

}

package com.ppc.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ppc.dao.DictDao;
import com.ppc.entity.Dict;
import com.ppc.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = DictService.class)
@Transactional
public class DictServiceImpl implements DictService {
    @Autowired
    private DictDao dictDao;
    @Override
    public List<Map<String, Object>> findZnodes(Long id) {
        List<Dict> dictList=dictDao.findListByParentId(id);

        List<Map<String, Object>> zNodes=new ArrayList<>();

        for (Dict dict : dictList) {
            Map map=new HashMap<>();
            map.put("id",dict.getId());
            map.put("name",dict.getName());
            map.put("isParent",dictDao.countParent(dict.getId())>0?true:false);
            zNodes.add(map);
        }
        return zNodes;
    }

    @Override
    public List<Dict> findListByParentId(Long parentId) {
        return dictDao.findListByParentId(parentId);
    }

    @Override
    public List<Dict> findListByDictCode(String dictCode) {
        Dict dict = dictDao.findDictbyDictCode(dictCode);

        return dictDao.findListByParentId(dict.getId());
    }
}

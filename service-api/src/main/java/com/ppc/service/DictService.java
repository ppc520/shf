package com.ppc.service;

import com.ppc.entity.Dict;

import java.util.List;
import java.util.Map;

public interface DictService {
    List<Map<String, Object>> findZnodes(Long id);

    List<Dict> findListByParentId(Long id);

    List<Dict> findListByDictCode(String dictCode);
}

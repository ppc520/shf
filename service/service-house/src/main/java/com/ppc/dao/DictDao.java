package com.ppc.dao;

import com.ppc.entity.Dict;

import java.util.List;

public interface DictDao {


    List<Dict> findListByParentId(Long parnetId);

    Integer countParent(Long id);

    Dict findDictbyDictCode(String dictCode);

    String getNameById(Long id);
}

package com.ppc.dao;

import com.ppc.entity.Community;

import java.util.List;

public interface CommunityDao extends BaseDao<Community>{

    List<Community> findAll();
}

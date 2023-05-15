package com.ppc.service;

import com.ppc.entity.Community;

import java.util.List;

public interface CommunityService extends BaseService<Community>{
    List<Community> findAll();
}

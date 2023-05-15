package com.ppc.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ppc.dao.BaseDao;
import com.ppc.dao.CommunityDao;
import com.ppc.dao.DictDao;
import com.ppc.entity.Community;
import com.ppc.service.CommunityService;
import com.ppc.util.CastUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = CommunityService.class)
@Transactional
public class CommunityServiceImpl extends BaseServiceImpl<Community> implements CommunityService {
    @Autowired
    private CommunityDao communityDao;
    @Autowired
    private DictDao dictDao;
    @Override
    protected BaseDao<Community> getEntityDao() {
        return communityDao;
    }

    @Override
    public PageInfo<Community> findPage(Map<String, Object> filters) {
        //当前页数
        int pageNum = CastUtil.castInt(filters.get("pageNum"), 1);
        //每页显示的记录条数
        int pageSize = CastUtil.castInt(filters.get("pageSize"), 10);

        PageHelper.startPage(pageNum, pageSize);

        Page<Community> page = communityDao.findPage(filters);

        for (Community community : page) {

            community.setAreaName(dictDao.getNameById(community.getAreaId()));

            community.setPlateName(dictDao.getNameById(community.getPlateId()));
        }
        return new PageInfo<>(page,10);
    }

    @Override
    public List<Community> findAll() {
        return communityDao.findAll();
    }

    @Override
    public Community getById(Serializable id) {
        Community community = communityDao.getById(id);

        community.setAreaName(dictDao.getNameById(community.getAreaId()));
        community.setPlateName(dictDao.getNameById(community.getPlateId()));

        return community;
    }
}

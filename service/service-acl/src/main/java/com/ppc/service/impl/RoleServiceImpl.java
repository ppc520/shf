package com.ppc.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.ppc.dao.BaseDao;
import com.ppc.dao.RoleDao;
import com.ppc.entity.Role;
import com.ppc.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service(interfaceClass = RoleService.class)
@Transactional
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {
    @Autowired
    RoleDao roleDao;

    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Override
    protected BaseDao<Role> getEntityDao() {
        return roleDao;
    }


}

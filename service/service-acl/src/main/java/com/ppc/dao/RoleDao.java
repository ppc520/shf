package com.ppc.dao;

import com.ppc.entity.Role;

import java.util.List;

public interface RoleDao extends BaseDao<Role> {
    List<Role> findAll();


}

package com.ppc.dao;

import com.ppc.entity.Permission;

import java.util.List;

public interface PermissionDao extends BaseDao<Permission> {
    List<Permission> findAll();

    List<Permission> getMenuPermissionByAdminId(Long userId);
}

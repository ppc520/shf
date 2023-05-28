package com.ppc.dao;

import com.ppc.entity.RolePermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RolePermissionDao extends BaseDao<RolePermission>{
    List<Long> findPermissionIdsByRoleId(Long roleId);

    void deletePermissionIdsByRoleId(Long roleId);

    void addPermissionIdAndRoleId(@Param("roleId") Long roleId,@Param("permissionId") Long permissionId);
}

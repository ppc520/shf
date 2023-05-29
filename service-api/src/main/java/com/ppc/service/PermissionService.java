package com.ppc.service;

import com.ppc.entity.Permission;

import java.util.List;
import java.util.Map;

public interface PermissionService extends BaseService<Permission> {
    List<Map<String, Object>> findPermissionsByRoleId(Long roleId);

    void assignPermission(Long roleId, Long[] permissionIds);

    List<Permission> getMenuPermissionByAdminId(Long userId);

    List<String> getPermissionCodesByAdminId(Long id);
}

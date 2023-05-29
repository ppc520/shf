package com.ppc.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ppc.dao.BaseDao;
import com.ppc.dao.PermissionDao;
import com.ppc.dao.RolePermissionDao;
import com.ppc.entity.Permission;
import com.ppc.helper.PermissionHelper;
import com.ppc.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = PermissionService.class)
@Transactional
public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements PermissionService {
    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private RolePermissionDao rolePermissionDao;
    @Override
    protected BaseDao<Permission> getEntityDao() {
        return permissionDao;
    }

    @Override
    public List<Map<String, Object>> findPermissionsByRoleId(Long roleId) {
        List<Permission> permissionList=permissionDao.findAll();
        List<Long> permissionIds=rolePermissionDao.findPermissionIdsByRoleId(roleId);
        List<Map<String, Object>> returnList=new ArrayList<>();
        for (Permission permission : permissionList) {
            Map<String,Object> map=new HashMap<>();
            map.put("id",permission.getId());
            map.put("pId",permission.getParentId());
            map.put("name",permission.getName());
            if (permissionIds.contains(permission.getId())){
                map.put("checked",true);
            }
            returnList.add(map);
        }
        return returnList;
    }

    @Override
    public void assignPermission(Long roleId, Long[] permissionIds) {
        rolePermissionDao.deletePermissionIdsByRoleId(roleId);
        for (Long permissionId : permissionIds) {
            if (permissionId!=null){
                rolePermissionDao.addPermissionIdAndRoleId(roleId,permissionId);
            }
        }
    }

    @Override
    public List<Permission> getMenuPermissionByAdminId(Long userId) {
        List<Permission> permissionList=null;
        if (userId==1){
            permissionList = permissionDao.findAll();
        }else {
            permissionList=permissionDao.getMenuPermissionByAdminId(userId);
        }
        List<Permission> treeList = PermissionHelper.bulid(permissionList);
        return treeList;
    }

    @Override
    public List<String> getPermissionCodesByAdminId(Long id) {
        List<String> permissionCodes=null;
        if (id==1){
            permissionCodes=permissionDao.getAllPermissionCodes();
        }else {
            permissionCodes=permissionDao.getPermissionCodesByAdminId(id);
        }
        return permissionCodes;
    }
}

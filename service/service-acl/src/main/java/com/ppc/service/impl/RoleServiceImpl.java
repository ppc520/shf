package com.ppc.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.ppc.dao.AdminRoleDao;
import com.ppc.dao.BaseDao;
import com.ppc.dao.RoleDao;
import com.ppc.entity.Role;
import com.ppc.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = RoleService.class)
@Transactional
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private AdminRoleDao adminRoleDao;

    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Override
    public Map<String, Object> findRolesByAdminId(Long adminId) {
        //获取所有角色
        List<Role> roleList = roleDao.findAll();
        //获取用户已有角色id
        List<Long> roleIds = adminRoleDao.findRoleIdsByAdminId(adminId);
        List<Role> noAssignRoleList = new ArrayList<>();
        List<Role> assignRoleList = new ArrayList<>();
        for (Role role : roleList) {
            if (roleIds.contains(role.getId())) {
                assignRoleList.add(role);
            } else {
                noAssignRoleList.add(role);
            }
        }
        Map<String, Object> roleMap = new HashMap<>();
        roleMap.put("assginRoleList", assignRoleList);
        roleMap.put("noAssginRoleList", noAssignRoleList);
        return roleMap;
    }

    @Override
    public void assignRole(Long adminId, Long[] roleIds) {
        adminRoleDao.deleteRoleIdsByAdminId(adminId);
        for (Long roleId : roleIds) {
            if (roleId != null) {
                adminRoleDao.addRoleIdAndAdminId(roleId, adminId);
            }
        }
    }

    @Override
    protected BaseDao<Role> getEntityDao() {
        return roleDao;
    }


}

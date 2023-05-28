package com.ppc.service;

import com.ppc.entity.Role;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Transactional
public interface RoleService extends BaseService<Role>{
     List<Role> findAll();

     Map<String,Object> findRolesByAdminId(Long adminId);

     void assignRole(Long adminId, Long[] roleIds);
}

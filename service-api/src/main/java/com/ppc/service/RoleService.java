package com.ppc.service;

import com.ppc.entity.Role;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface RoleService extends BaseService<Role>{
     List<Role> findAll();

}

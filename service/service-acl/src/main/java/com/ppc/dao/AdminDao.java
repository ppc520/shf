package com.ppc.dao;

import com.ppc.entity.Admin;

import java.util.List;

public interface AdminDao extends BaseDao<Admin>{
    List<Admin> findAll();

    Admin getAdminByUsername(String username);
}

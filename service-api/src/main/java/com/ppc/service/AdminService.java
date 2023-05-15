package com.ppc.service;

import com.ppc.entity.Admin;

import java.util.List;

public interface AdminService extends BaseService<Admin>{
    List<Admin> findAll();
}

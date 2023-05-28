package com.ppc.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ppc.dao.BaseDao;
import com.ppc.dao.UserInfoDao;
import com.ppc.entity.UserInfo;
import com.ppc.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service(interfaceClass = UserInfoService.class)
@Transactional
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfo> implements UserInfoService {
    @Autowired
    private UserInfoDao userInfoDao;
    @Override
    protected BaseDao<UserInfo> getEntityDao() {
        return userInfoDao;
    }

    @Override
    public UserInfo getUserInfoByPhone(String phone) {
        return userInfoDao.getUserInfoByPhone(phone);
    }
}

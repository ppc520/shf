package com.ppc.dao;

import com.ppc.entity.UserInfo;

public interface UserInfoDao extends BaseDao<UserInfo>{

    UserInfo getUserInfoByPhone(String phone);
}

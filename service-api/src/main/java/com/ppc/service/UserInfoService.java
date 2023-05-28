package com.ppc.service;

import com.ppc.entity.UserInfo;

public interface UserInfoService extends BaseService<UserInfo> {
    UserInfo getUserInfoByPhone(String phone);
}

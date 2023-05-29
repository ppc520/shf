package com.ppc.config;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ppc.entity.Admin;
import com.ppc.service.AdminService;
import com.ppc.service.PermissionService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserDetailServiceImpl implements UserDetailsService {
    @Reference
    private AdminService adminService;
    @Reference
    private PermissionService permissionService;
    //登陆时，SpringSecurity会自动调用该方法，并将用户名传入该方法中
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin=adminService.getAdminByUsername(username);
        if (admin==null){
            throw new UsernameNotFoundException("用户不存在");
        }
        //调用permissionService中获取权限吗
        List<String> permissionList=permissionService.getPermissionCodesByAdminId(admin.getId());
        List<GrantedAuthority> grantedAuthorityList=new ArrayList<>();
        for (String s : permissionList) {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(s);
            grantedAuthorityList.add(simpleGrantedAuthority);
        }
        //给用户授权
//        User user = new User(username, admin.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList(""));
        User user=new User(username,admin.getPassword(),grantedAuthorityList);
        return user;

    }
}

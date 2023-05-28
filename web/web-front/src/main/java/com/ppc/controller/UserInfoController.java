package com.ppc.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ppc.entity.UserInfo;
import com.ppc.result.Result;
import com.ppc.result.ResultCodeEnum;
import com.ppc.service.UserInfoService;
import com.ppc.vo.LoginVo;
import com.ppc.vo.RegisterVo;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/userInfo")
public class UserInfoController {
    @Reference
    private UserInfoService userInfoService;
    @GetMapping("/sendCode/{phone}")
    public Result sendCode(@PathVariable String phone, HttpServletRequest request){
        String code="8888";
        request.getSession().setAttribute("code",code);
        return Result.ok(code);
    }
    @PostMapping("/register")
    public Result register(@RequestBody RegisterVo registerVo,HttpServletRequest request){
        String code = registerVo.getCode();
        String nickName = registerVo.getNickName();
        String password = registerVo.getPassword();
        String phone = registerVo.getPhone();
        if (StringUtils.isEmpty(code)||StringUtils.isEmpty(nickName)||StringUtils.isEmpty(password)||StringUtils.isEmpty(phone)){
            return Result.build(null, ResultCodeEnum.PARAM_ERROR);
        }
        Object o = request.getSession().getAttribute("code");
        if(!code.equals((String) o)){
            return Result.build(null,ResultCodeEnum.CODE_ERROR);
        }
        UserInfo userInfo=userInfoService.getUserInfoByPhone(phone);
        if (userInfo!=null){
            return Result.build(null,ResultCodeEnum.PHONE_REGISTER_ERROR);
        }
        UserInfo userInfo1=new UserInfo();
        userInfo1.setNickName(nickName);
        userInfo1.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        userInfo1.setPhone(phone);
        userInfo1.setStatus(1);
        userInfoService.insert(userInfo1);
        return Result.ok();
    }
    @PostMapping("/login")
    public Result login(@RequestBody LoginVo loginVo,HttpServletRequest request){
        String phone = loginVo.getPhone();
        String password = loginVo.getPassword();
        if (StringUtils.isEmpty(password)||StringUtils.isEmpty(phone)){
            return Result.build(null,ResultCodeEnum.PARAM_ERROR);
        }
        UserInfo userInfo = userInfoService.getUserInfoByPhone(phone);
        if (userInfo==null){
            return Result.build(null,ResultCodeEnum.ACCOUNT_ERROR);
        }
        if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(userInfo.getPassword())){
            return Result.build(null,ResultCodeEnum.ACCOUNT_ERROR);
        }
        if (userInfo.getStatus()==0){
            return Result.build(null,ResultCodeEnum.ACCOUNT_LOCK_ERROR);
        }
        request.getSession().setAttribute("user",userInfo);
        Map map=new HashMap();
        map.put("nickName",userInfo.getNickName());
        map.put("phone",phone);
        return Result.ok(map);
    }
    @GetMapping("/logout")
    public Result logout(HttpServletRequest request){
        request.getSession().removeAttribute("user");
        //request.getSession().invalidate();
        return Result.ok();
    }
}

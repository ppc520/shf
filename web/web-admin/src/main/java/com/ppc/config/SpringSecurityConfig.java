package com.ppc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableGlobalMethodSecurity(prePostEnabled = true)//开启controller中方法权限控制
@Configuration
@EnableWebSecurity//开启SpringSecurity自动配置，会自动生成一个登陆界面
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    //在内存中设置一个认证的用户名密码
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().withUser("admin")
//                .password(getPasswordEncoder().encode("123456"))
//                .roles("");
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http);//必须继承，否则验证会前面的配置都会失效，除非自己配置
        //配置允许iframe访问
        http.headers().frameOptions().disable();
        //配置可以匿名访问的资源
        http.authorizeRequests().antMatchers("/static/**","/login").permitAll().anyRequest().authenticated();
        //配置自定义登陆界面
        http.formLogin().loginPage("/login")//配置去自定义界面的路径
                .defaultSuccessUrl("/");//配置登陆成功之后去往的地址
        //配置登出界面
        http.logout().logoutUrl("/logout")
                .logoutSuccessUrl("/login");
        //关闭跨域请求伪造
        http.csrf().disable();
        //配置自定义的无权限访问的处理器
        http.exceptionHandling().accessDeniedPage("/auth");
    }

    //将BCryptPasswordEncoder加载到Spring容器中
    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}

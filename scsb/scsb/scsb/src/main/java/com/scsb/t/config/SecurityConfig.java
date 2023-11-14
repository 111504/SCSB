package com.scsb.t.config;

import com.scsb.t.filter.NoCacheFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

//若要自訂登入邏輯則要繼承WebSecurityConfiguration
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 表單提交
        http.formLogin()
                .loginProcessingUrl("/login")
                .loginPage("/login")
                .failureUrl("/login?error=true")//登入失敗跳回登入頁
                .defaultSuccessUrl("/index")//可以讓登入後更新網址
//                .successForwardUrl("/index")
                //.failureForwardUrl("/fail")
                .usernameParameter("username")
                .passwordParameter("password")

                .and()
                .headers()
                    .cacheControl().disable()

                .and()
                // 授權認證
                .authorizeRequests()
                //允許訪問資源
                .antMatchers("/static/**").permitAll() //表示static底下資料夾都不需要認證
                .antMatchers("/templates/forms/formTemplate/**").permitAll()
                .antMatchers("/templates/forms/signature/**").permitAll()
                .antMatchers("/api/**").permitAll() // Allow access to API without authentication

                //不需要被認證的頁面
                .antMatchers("/login").permitAll()
//                .antMatchers("/index").permitAll()
//                .antMatchers("/addformV2").permitAll()
//                .antMatchers("/Dyn_formGUI").permitAll()
//                .antMatchers("/incident-table").permitAll()
//                .antMatchers("/unreview-table").permitAll()
                //必須要有admin權限才可以訪問
                .antMatchers("/adminpage").hasAuthority("admin")
                //必須要有manager角色才可以訪問
                .antMatchers("/managerpage").hasRole("manager")
                //其他指定任意角色都可以被訪問
                .antMatchers("/employeepage").hasAnyRole("manager", "employee")
                //其他都需要被認證
                .anyRequest().authenticated()

                .and()
                // 登出
                .logout()
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutSuccessUrl("/login")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // 可以使用任何的 HTTP 方法登出

                .and()
                // 勿忘我（remember-me）
                .rememberMe()
                .userDetailsService(userDetailsService)
                .tokenValiditySeconds(300) // 通常都會大於 session timeout 的時間

                .and()
                .addFilterBefore(new NoCacheFilter(), UsernamePasswordAuthenticationFilter.class);





    }



    //[規定]要建立密碼演算的實例，必須透過Bean注入 SHA-256
    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}

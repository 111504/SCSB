package com.scsb.t.service;

import com.scsb.t.entity.EmployeeEntity;
import com.scsb.t.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
//
//    @Autowired
//    private UserDAO userDAO;
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        //1.查詢用戶是否存在
//        Optional<Map.Entry<String, Map<String, String>>> opt = userDAO.users
//                .entrySet()
//                .stream()
//                .filter(e -> e.getKey().equals(username))
//                .findFirst();
//        if(!opt.isPresent()) throw  new UsernameNotFoundException("Not found");
//
//        //2.取得資料並進行密碼比對
//        Map<String, String> info = opt.get().getValue();
//        String password = info.get("password");
//        String authority = info.get("authority");
//        return new User(username,
//                        password,
//                        AuthorityUtils.commaSeparatedStringToAuthorityList(authority));
//    }

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. 查詢用戶是否存在
        EmployeeEntity employeeEntity = userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not found"));

        // 2. 取得資料並進行密碼比對
        String password = employeeEntity.getPassword();

        String authority = employeeEntity.getAuthority();


        return new org.springframework.security.core.userdetails.User(
                username,
                password,
                AuthorityUtils.commaSeparatedStringToAuthorityList(authority));
    }



}
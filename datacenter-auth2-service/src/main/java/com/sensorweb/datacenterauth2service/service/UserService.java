package com.sensorweb.datacenterauth2service.service;

import com.sensorweb.datacenterauth2service.dao.*;
import com.sensorweb.datacenterauth2service.entity.*;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private ResRoleMapper resRoleMapper;

    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userMapper.selectByUsername(s);
        if (user==null) {
            throw new UsernameNotFoundException("用户名不存在!");
        }
        List<UserRole> userRoles = userRoleMapper.selectByUid(user.getId());
        if (userRoles==null || userRoles.size()==0) {
            return null;
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (UserRole userRole : userRoles) {
            Role role = roleMapper.selectById(userRole.getRid());
            if (role!=null) {
                authorities.add(new SimpleGrantedAuthority(role.getName()));
            }
        }
//        List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(roles.toString());
        user.setAuthorities(authorities);
        return user;
    }

    /**
     * 通过role名称查匹配路径path
     */
    public List<String> getPathByRoleName(String name) {
        List<String> paths = new ArrayList<>();
        Role role = roleMapper.selectByName(name);
        if (role==null) {
            return paths;
        }
        List<ResRole> resRoles = resRoleMapper.selectByResId(role.getId());
        if (resRoles==null || resRoles.size()<1) {
            return paths;
        }

        for (ResRole resRole:resRoles) {
            Resource resource = resourceMapper.selectById(resRole.getResId());
            paths.add(resource.getPath());
        }
        return paths;
    }
}

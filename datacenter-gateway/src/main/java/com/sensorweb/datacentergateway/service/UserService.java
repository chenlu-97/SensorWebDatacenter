package com.sensorweb.datacentergateway.service;

import com.sensorweb.datacentergateway.dao.*;
import com.sensorweb.datacentergateway.entity.ResRole;
import com.sensorweb.datacentergateway.entity.Resource;
import com.sensorweb.datacentergateway.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private ResRoleMapper resRoleMapper;

    @Autowired
    private ResourceMapper resourceMapper;

   /**
     * 通过role名称查匹配路径path
     */
    public List<String> getPathByRoleName(String name) {
        List<String> paths = new ArrayList<>();
        Role role = roleMapper.selectByName(name);
        if (role==null) {
            return paths;
        }
        List<ResRole> resRoles = resRoleMapper.selectByRid(role.getId());
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

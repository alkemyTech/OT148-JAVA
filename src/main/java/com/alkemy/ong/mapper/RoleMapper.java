package com.alkemy.ong.mapper;

import com.alkemy.ong.domain.Role;
import com.alkemy.ong.repository.model.RoleModel;

public class RoleMapper {
    public static Role RoleModelToDomain(RoleModel roleModel){
        Role roleDomain = new Role();
        roleDomain.setName(roleModel.getName());
        roleDomain.setDescription(roleModel.getDescription());
        return roleDomain;
    }
    public static RoleModel RoleDomainToModel (Role roleDomain){
        RoleModel roleModel = new RoleModel();
        roleModel.setName(roleDomain.getName());
        roleModel.setDescription(roleDomain.getDescription());
        return roleModel;
    }
}

package com.alkemy.ong.mapper;

import com.alkemy.ong.domain.Role;
import com.alkemy.ong.repository.model.RoleModel;

public class RoleMapper {
    public static Role mapModelToDomain(RoleModel roleModel){
        Role roleDomain = Role.builder().
                name(roleModel.getName()).
                description(roleModel.getDescription()).build();
        return roleDomain;
    }
    public static RoleModel mapDomainToModel (Role roleDomain){
        RoleModel roleModel = RoleModel.builder().
                name(roleDomain.getName()).
                description(roleDomain.getDescription()).build();
        return roleModel;
    }
}

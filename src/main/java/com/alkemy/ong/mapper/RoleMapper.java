package com.alkemy.ong.mapper;

import com.alkemy.ong.domain.Role;
import com.alkemy.ong.repository.model.RoleModel;
import com.alkemy.ong.util.RoleName;

public class RoleMapper {
    public static Role mapModelToDomain(RoleModel roleModel) {
        Role roleDomain = Role.builder()
                .id(roleModel.getId())
                .name(RoleName.valueOf(roleModel.getName()))
                .description(roleModel.getDescription())
                .creationDate(roleModel.getCreationDate())
                .build();
        return roleDomain;
    }

    public static RoleModel mapDomainToModel(Role roleDomain) {
        RoleModel roleModel = RoleModel.builder()
                .id(roleDomain.getId())
                .name(roleDomain.getName().toString())
                .description(roleDomain.getDescription())
                .creationDate(roleDomain.getCreationDate())
                .build();
        return roleModel;
    }
}

package com.alkemy.ong.util;

public enum RoleName {
    ROLE_ADMIN, ROLE_USER;

    public static RoleName parse(String text) {
        if (ROLE_ADMIN.name().equalsIgnoreCase(text)) {
            return ROLE_ADMIN;
        } else {
            return ROLE_USER;
        }
    }
}

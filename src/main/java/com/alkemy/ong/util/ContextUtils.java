package com.alkemy.ong.util;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class ContextUtils {

    public static String currentContextPath() {
        String currentContextPath = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .toUriString();
        return currentContextPath;
    }
}
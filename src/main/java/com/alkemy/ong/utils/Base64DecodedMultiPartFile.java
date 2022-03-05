package com.alkemy.ong.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.web.multipart.MultipartFile;

public class Base64DecodedMultiPartFile implements MultipartFile {

    private final byte[] imageContent;

    public Base64DecodedMultiPartFile(byte[] imageContent) {
        this.imageContent = imageContent;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getOriginalFilename() {
        return null;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return imageContent == null || imageContent.length == 0;
    }

    @Override
    public long getSize() {
        return imageContent.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return imageContent;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(imageContent);
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        new FileOutputStream(dest).write(imageContent);
    }
}

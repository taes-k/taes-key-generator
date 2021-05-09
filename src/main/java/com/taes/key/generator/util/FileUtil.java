package com.taes.key.generator.util;

import org.apache.commons.io.FileUtils;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FileUtil
{
    private FileUtil()
    {
        throw new IllegalStateException("Utility class");
    }

    public static String fileToString(String path) throws IOException
    {
        return FileUtils.readFileToString(ResourceUtils.getFile(path), StandardCharsets.UTF_8);
    }
}

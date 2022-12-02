package com.api.automation.util;

import java.io.File;
import java.util.logging.Logger;

public class PackageUtil {
    private static final Logger LOGGER = Logger.getLogger(PackageUtil.class.getName());
    public static void recursiveDelete(File file) {
        if (!file.exists())
            return;
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                recursiveDelete(f);
            }
        }
        file.delete();
    }
}

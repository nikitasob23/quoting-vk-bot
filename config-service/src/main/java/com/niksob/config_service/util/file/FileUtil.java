package com.niksob.config_service.util.file;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@Slf4j
public class FileUtil {
    public void dropDir(String dirName) {
        try {
            File dir = new File(dirName);
            if (dir.exists()) {
                if (dir.isDirectory()) {
                    File[] files = dir.listFiles();
                    if (files != null) {
                        for (File file : files) {
                            dropDir(file.getAbsolutePath());
                        }
                    }
                }
                dir.delete();
                log.debug("File was deleted: {}", dir);
            }
        } catch (Exception e) {
            log.error("Error during deleting directory: {}", dirName, e);
        }
    }
}

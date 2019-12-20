package ru.itpark.service;

import ru.itpark.enums.RfcPath;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class RfcPatchService implements RfcPathService {
    private final String uploadPath;
    private final String tmpPath;
    private static RfcPatchService rfcPatchService;

    private RfcPatchService() {
        uploadPath = System.getenv(RfcPath.UPLOAD_PATH.toString());
        tmpPath = System.getenv(RfcPath.TMP_PATH.toString());
        try {
            Files.createDirectories(Paths.get(uploadPath));
            Files.createDirectories(Paths.get(tmpPath));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static RfcPatchService getInstance() {
        if (rfcPatchService == null) {
            rfcPatchService = new RfcPatchService();
        }
        return rfcPatchService;
    }


    public String getPath(RfcPath path) {
        if (path.equals(RfcPath.UPLOAD_PATH)) {
            return uploadPath;
        }

        if (path.equals(RfcPath.TMP_PATH)) {
            return tmpPath;
        }

        return null;
    }
}

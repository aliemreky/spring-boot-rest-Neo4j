package com.volaid.volaid.service.GeneralService;

import com.volaid.volaid.util.MimeTypes;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageUploadService {

    public final String UPLOAD_PATH = new ClassPathResource("src/main/resources/static/uploads").getPath();

    public String SingleImageUpload(MultipartFile file) {

        String sourceFileName = file.getOriginalFilename();
        String sourceFileNameExtension = FilenameUtils.getExtension(sourceFileName).toLowerCase();

        String destinationFileName = RandomStringUtils.randomAlphanumeric(32) + "." + sourceFileNameExtension;

        if (MimeTypes.lookupMimeType(sourceFileNameExtension)) {

            byte[] bytes;

            try {

                bytes = file.getBytes();
                Path path = Paths.get(UPLOAD_PATH + File.separator + destinationFileName);
                Files.write(path, bytes);

            } catch (IOException e) {
                return null;
            }

            return destinationFileName;

        } else {
            return null;
        }
    }

}

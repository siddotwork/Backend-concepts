package com.app.civicfix.Service;

import com.app.civicfix.Entity.ImageData;
import com.app.civicfix.Repository.StorageRepository;
import com.app.civicfix.Util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class StorageService {
    @Autowired
    private StorageRepository ss;

    public String uploadImage(MultipartFile file) throws IOException {
        ImageData imageData = ss.save(ImageData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtil.compressImage(file.getBytes()))
                .build());

        if (imageData != null) {
            return "File uploaded successfully: " + file.getOriginalFilename();
        }
        return null;
    }

    public byte[] downloadImage(String filename) {
        Optional<ImageData> dbImageData = ss.findByName(filename);

        if (dbImageData.isEmpty()) {
            return null;
        }

        return ImageUtil.decompressImage(dbImageData.get().getImageData());
    }

    public String getImageContentType(String filename) {
        Optional<ImageData> dbImageData = ss.findByName(filename);
        return dbImageData.map(ImageData::getType).orElse(null);
    }
    public boolean deleteImage(String filename) {
        Optional<ImageData> dbImageData = ss.findByName(filename);
        if (dbImageData.isPresent()) {
            ss.delete(dbImageData.get());
            return true;
        }
        return false;
    }
}

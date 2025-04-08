package com.app.civicfix.Controller;

import com.app.civicfix.Entity.User;
import com.app.civicfix.Service.StorageService;
import com.app.civicfix.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/image")
public class ImageController {
//    @Autowired
//    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private StorageService ss;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
        String response = ss.uploadImage(file);
        if (response != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Image upload failed");
    }

    @GetMapping("/{imageName}")
    public ResponseEntity<byte[]> downloadImage(@PathVariable String imageName) {
        byte[] imageByte = ss.downloadImage(imageName);

        if (imageByte == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }


        String contentType = ss.getImageContentType(imageName);
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.parseMediaType(contentType))
                .body(imageByte);
    }

    @DeleteMapping("/{imageName}")
    public ResponseEntity<String> deleteImage(@PathVariable String imageName) {
        boolean deleted = ss.deleteImage(imageName);
        if (deleted) {
            return ResponseEntity.status(HttpStatus.OK).body("Image deleted successfully: " + imageName);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Image not found: " + imageName);
    }


}

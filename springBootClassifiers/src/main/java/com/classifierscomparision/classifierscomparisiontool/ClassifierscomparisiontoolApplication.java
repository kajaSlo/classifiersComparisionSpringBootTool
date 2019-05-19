package com.classifierscomparision.classifierscomparisiontool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

//@CrossOrigin(origins = "http://localhost:4200")
@SpringBootApplication
//@RestController
public class ClassifierscomparisiontoolApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClassifierscomparisiontoolApplication.class, args);
    }

//    @RequestMapping(value="/upload", method= RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<Object> uploadFile (@RequestParam("file") MultipartFile file) {
//        try {
//            byte[] bytes = file.getBytes();
//            Path path = Paths.get("/home/kaja/Pulpit/PracaMagisterska/testFileUpload/" + file.getOriginalFilename());
//            Files.write(path, bytes);
//
//        }catch (IOException e) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//
//        Double result = 3.14567789;
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }
}

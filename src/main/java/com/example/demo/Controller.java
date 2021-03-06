package com.example.demo;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
public class Controller {


    private final Logger logger = LoggerFactory.getLogger(Controller.class);

    //Save the uploaded file to this folder

    //private static String UPLOAD_FOLDER = "out/uploads/"; //bei lokalem run
    private static String UPLOAD_FOLDER = "/usr/app/uploads/"; // Bei Docker Run


    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {

        logger.debug("Single file is uploaded");

        if (file.isEmpty()) {
            return new ResponseEntity("please select a file!", HttpStatus.BAD_REQUEST);
        }

        File directory = new File(UPLOAD_FOLDER);
        if (! directory.exists()){
            directory.mkdir();
            // If you require it to make the entire directory path including parents,
            // use directory.mkdirs(); here instead.
        }
        try {
            saveUploadedFiles(Arrays.asList(file));
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.ok("Successfully uploaded - " + file.getOriginalFilename());

    }

    @GetMapping(path = "/download")
    public ResponseEntity<byte[]> download(@RequestParam String filename) throws IOException {

        logger.debug(String.format("FILENAME: %s", filename));

        File file = new File(UPLOAD_FOLDER + filename);
        Path path = Paths.get(file.getAbsolutePath());
        byte[] fileContent = Files.readAllBytes(path);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"");

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(fileContent);
    }

    @GetMapping
    ResponseEntity<String> getHello() {
        return ResponseEntity.ok("hiii");
    }



    //save file
    private void saveUploadedFiles(List<MultipartFile> files) throws IOException {

        for (MultipartFile file : files) {

            if (file.isEmpty()) {
                continue; //next pls
            }

            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOAD_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);

        }

    }


}

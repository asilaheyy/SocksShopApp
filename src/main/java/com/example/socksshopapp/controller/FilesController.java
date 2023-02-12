package com.example.socksshopapp.controller;

import com.example.socksshopapp.service.FileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
    @RestController
    @RequestMapping("files")
    @Tag(name = "Файлы", description = "Загрузка и скачивание файлов")
    public class FilesController {

        private final FileService fileService;

        public FilesController(FileService fileService) {
            this.fileService = fileService;
        }

        @GetMapping("/export")
        public ResponseEntity<InputStreamResource> downloadFile() throws FileNotFoundException {
            File file = fileService.getDataFile();

            if (file.exists()) {
                InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .contentLength(file.length())
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"SocksShopLog.json\"")
                        .body(resource);
            } else {
                return ResponseEntity.noContent().build();
            }
        }

        @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        public ResponseEntity<Void> uploadDataFile(@RequestParam MultipartFile file) {
            fileService.cleanDataFile();
            File dataFile = fileService.getDataFile();

            try (FileOutputStream fos = new FileOutputStream(dataFile)) {

                IOUtils.copy(file.getInputStream(), fos);
                return ResponseEntity.ok().build();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }


    }



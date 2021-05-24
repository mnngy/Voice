package com.example.demo.jh.controller;

import com.example.demo.jh.repository.FileUploadRepository;
import com.example.demo.jh.storage.StorageException;
import com.example.demo.jh.storage.StorageFileNotFoundException;
import com.example.demo.jh.storage.StorageService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@Controller
public class FileUploadController {
    final static Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    private final StorageService storageService;

    @Autowired
    public FileUploadController(StorageService storageService, FileUploadRepository fileUploadRepository) {
        this.storageService = storageService;
        this.fileUploadRepository = fileUploadRepository;
        System.out.println("constructor");
    }

    @GetMapping("/upload")
    public String listUploadedFiles(Model model) throws IOException {
    /*
        model.addAttribute("files", storageService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                        "serveFile", path.getFileName().toString()).build().toString())
                .collect(Collectors.toList()));
               */
        //여기는 존재하는 파일을 불러오는 장소

        System.out.println("GetMapping");

        return "upload";
    }

    /*
    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) throws IOException {
        Resource file = storageService.loadAsResource(filename);

        System.out.println(file.getFilename());

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }*/

    @Autowired
    private final FileUploadRepository fileUploadRepository;


    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("upload") MultipartFile file, @RequestParam("uploadmp3") MultipartFile file2,
                                   RedirectAttributes redirectAttributes, HttpServletRequest request) {

        /*
        if(file==null){

            response.setContentType("text/html; charset=UTF-8");

            PrintWriter out = response.getWriter();

            out.println("<script>alert('계정이 등록 되었습니다'); location.href='이동주소';</script>");

            out.flush();

        }*/
        //업로드 시작
        System.out.println("PostMapping");

        String message = "You successfully uploaded " + file.getOriginalFilename() + "!";
        try {
            storageService.store(file,request);
            storageService.store(file2,request);
            fileUploadRepository.insetUpload(request,file,file2);


        } catch (StorageException | SQLException e) {
            message = "Something happened with file " + file.getOriginalFilename() + ".";
        }
        redirectAttributes.addFlashAttribute("message", message);

        return "upload";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}

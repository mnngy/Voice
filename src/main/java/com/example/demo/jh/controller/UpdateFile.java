package com.example.demo.jh.controller;

import com.example.demo.jh.repository.UpdateFileRepository;
import com.example.demo.jh.controller.MyPageController;
import com.example.demo.jh.storage.StorageException;
import com.example.demo.jh.storage.StorageFileNotFoundException;
import com.example.demo.jh.storage.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;

@Controller
public class UpdateFile {
    private final MyPageController myPageController;
    int boardIdx;
    final static Logger logger = LoggerFactory.getLogger(UpdateFile.class);

    private final StorageService storageService;


    @Autowired
    public UpdateFile(StorageService storageService, UpdateFileRepository updateFileRepository,MyPageController myPageController) {
        this.storageService = storageService;
        this.updateFileRepository = updateFileRepository;
        this.myPageController =myPageController;
        System.out.println("constructor");
    }



    @GetMapping("/updateFile")
    public String listUploadedFiles(Model model,int boardIdx) throws IOException {
        this.boardIdx=boardIdx;
        System.out.println("GetMapping");

        return "updateFile";
    }



    @Autowired
    private final UpdateFileRepository updateFileRepository;


    @PostMapping("/updateFile")
    public String handleFileUpload(@RequestParam("upload") MultipartFile file, @RequestParam("uploadmp3") MultipartFile file2,
                                   RedirectAttributes redirectAttributes, HttpServletRequest request,Model model) throws SQLException {


        //업로드 시작
        System.out.println("updateFile");

        String message = "You successfully update " + file.getOriginalFilename() + "!";
        try {
            storageService.store(file,request);
            storageService.store(file2,request);
            updateFileRepository.updateUpload(request,file,file2,boardIdx);


        } catch (StorageException | SQLException e) {
            message = "Something happened with file " + file.getOriginalFilename() + ".";
        }
        redirectAttributes.addFlashAttribute("message", message);

        myPageController.myPagePrint(request,model);
        return "myPage";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/delete")
    public String delete(int boardIdx,HttpServletRequest request, Model model) throws SQLException {

        updateFileRepository.deleteFile(boardIdx);
        myPageController.myPagePrint(request,model);
        return "myPage";
    }
}

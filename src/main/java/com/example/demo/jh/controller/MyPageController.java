package com.example.demo.jh.controller;



import com.example.demo.jh.domain.MyPageBoard;
import com.example.demo.jh.repository.MyPageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

@Controller
public class MyPageController {

    @Autowired
    private final MyPageRepository myPageRepository;

    public MyPageController(MyPageRepository myPageRepository) {
        this.myPageRepository = myPageRepository;
    }


    @GetMapping("/myPage")
    public String myPagePrint(HttpServletRequest request, Model model) throws SQLException {
        List myBord;
        String ID;
        HttpSession session = request.getSession();
        ID =(String) session.getAttribute("sessionMemberId");
        List<MyPageBoard> myPageBoardList = null;
        try {
            myPageBoardList = myPageRepository.MyPage(ID);
            model.addAttribute("myPageBoardList", myPageBoardList);
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }


        myBord = myPageRepository.MyPage(ID);
        return "myPage";
    }
}

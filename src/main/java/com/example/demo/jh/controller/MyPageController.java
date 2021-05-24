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

        String ID;
        HttpSession session = request.getSession();
        ID =(String) session.getAttribute("sessionMemberId");
        List<MyPageBoard> myPageBoardList = null;
        Long count=null;
        Long follower=null;
        Long following=null;
        try {
            myPageBoardList = myPageRepository.MyPage(ID);
            String name;
           MyPageBoard myPageBoard = myPageBoardList.get(0);
           name =myPageBoard.memberID;
           model.addAttribute("myPageBoardList", myPageBoardList);
           model.addAttribute("name",name);
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        try{
            count =myPageRepository.Count(ID);
            follower=myPageRepository.Follower(ID);
            following=myPageRepository.Following(ID);
            model.addAttribute("countBord", count);
            model.addAttribute("follower", follower);
            model.addAttribute("following", following);
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }


        return "myPage";
    }

    @GetMapping("/ProfilePage")
    public String youPagePrint(HttpServletRequest request, Model model,String userID) throws SQLException {

        String ID;
        HttpSession session = request.getSession();
        ID =(String) session.getAttribute("sessionMemberId");
        if(ID.equals(userID)){
            System.out.println("ok");
            myPagePrint(request,model);
            return "myPage";
        }
        List<MyPageBoard> myPageBoardList = null;
        Long count=null;
        Long follower=null;
        Long following=null;
        try {
            myPageBoardList = myPageRepository.MyPage(userID);
            String name;
            MyPageBoard myPageBoard = myPageBoardList.get(0);
            name =myPageBoard.memberID;
            model.addAttribute("myPageBoardList", myPageBoardList);
            model.addAttribute("name",name);
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        try{
            count =myPageRepository.Count(userID);
            follower=myPageRepository.Follower(userID);
            following=myPageRepository.Following(userID);
            model.addAttribute("countBord", count);
            model.addAttribute("follower", follower);
            model.addAttribute("following", following);
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }


        return "ProfilePage";
    }
}

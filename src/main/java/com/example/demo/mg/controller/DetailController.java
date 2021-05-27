package com.example.demo.mg.controller;

import com.example.demo.mg.domain.Board;
import com.example.demo.mg.domain.Comment;
import com.example.demo.mg.repository.DetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

@Controller
public class DetailController {

    @Autowired
    private final DetailRepository detailRepository;

    public DetailController(DetailRepository detailRepository) {
        this.detailRepository = detailRepository;
    }


    @GetMapping("detail")
    public String Commentprint(Model model, Comment comment, Board board, HttpServletRequest request)
    {
        String ID;
        HttpSession session = request.getSession();
        ID =(String) session.getAttribute("sessionMemberId");
        List<Comment> commentList = null;
        try {
            Long idx = detailRepository.MemberIDXselect(ID);
            commentList = detailRepository.Detailcommentselect(board);
            Board board1 = detailRepository.DetailBoardselect(board);
            Long memberidx = detailRepository.BoardIDXselect(board);
            int writer = detailRepository.Writerselect(idx, board);
            System.out.println(memberidx);
            int count = detailRepository.countLike(board);
            int result = detailRepository.serchFollow(idx, memberidx);
            String follow="";
            model.addAttribute("commentList",commentList);
            model.addAttribute("boardImage", board1.getBoardImage());
            model.addAttribute("boardAudio", board1.getBoardAudio());
            model.addAttribute("memberId",board1.getMemberId());
            model.addAttribute("boardDate",board1.getBoardDate());
            model.addAttribute("boardIdx", board1.getBoardIdx());
            model.addAttribute("likescore", count);
            model.addAttribute("writer", writer);
            if (result==1)
                follow="팔로우 중";
            else if(result==0)
                follow="팔로우";
            else
                follow="에러";
            model.addAttribute("follow", follow);
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }

        return "detail";
    }

    @PostMapping("detail")
    public ModelAndView commentrPost(Comment comment, Board board, HttpServletRequest request) {
        String ID;
        HttpSession session = request.getSession();
        ID =(String) session.getAttribute("sessionMemberId");
        try {
            Long idx = detailRepository.MemberIDXselect(ID);
            detailRepository.setComment(comment, board, idx);
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return new ModelAndView("redirect:/detail?boardIdx="+board.getBoardIdx());
    }
    @PostMapping("like")
    @ResponseBody
    public int likePost(Model model, Board board, HttpServletRequest request) {
        String ID;
        HttpSession session = request.getSession();
        ID =(String) session.getAttribute("sessionMemberId");
        try {
            Long idx = detailRepository.MemberIDXselect(ID);
            int result = detailRepository.serchLike(board, idx);
            switch (result){
                case -1:
                    break;
                case 0:
                    detailRepository.insertLike(board, idx);
                    break;
                case 1:
                    detailRepository.deleteLike(board, idx);
                    break;
            }
            int count = detailRepository.countLike(board);
            model.addAttribute("likescore", count);
            model.addAttribute("boardIdx", board.getBoardIdx());
            return count;

        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        model.addAttribute("boardIdx", board.getBoardIdx());
        return -1;
    }
    @PostMapping("follow")
    @ResponseBody
    public String followPost(Model model, Comment comment, Board board, HttpServletRequest request, @RequestParam(value = "boardIdx", required=false) String boardIdx) {
        String ID;
        HttpSession session = request.getSession();
        ID =(String) session.getAttribute("sessionMemberId");
        String rs="";
        try {
            Long idx = detailRepository.MemberIDXselect(ID);
            Long boardIdx2 = Long.parseLong(boardIdx);//
            Long memberidx = detailRepository.BoardIDXselect2(boardIdx2);//
            System.out.println("팔로우 : "+memberidx);
            int result = detailRepository.serchFollow(idx, memberidx);
            switch (result){
                case -1:
                    rs="에러";
                    break;
                case 0:
                    detailRepository.insertFollow(idx, memberidx);
                    rs = "팔로우 중";
                    break;
                case 1:
                    detailRepository.deleteFollow(idx, memberidx);
                    rs = "팔로우";
                    break;
            }
            model.addAttribute("follow", rs);
            model.addAttribute("boardIdx", board.getBoardIdx());
            return rs;

        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        model.addAttribute("boardIdx", board.getBoardIdx());
        return rs;
    }



    @PostMapping("follow2")
    @ResponseBody
    public String followPost2(Model model, Comment comment, Board board, HttpServletRequest request, @RequestParam(value = "memberID", required=false) String memberID) {
        String ID;
        HttpSession session = request.getSession();
        ID =(String) session.getAttribute("sessionMemberId");
        String rs="";
        try {
            Long idx = detailRepository.MemberIDXselect(ID);
            Long memberidx = detailRepository.BoardIDXselect3(memberID);//
            System.out.println("팔로우 : "+memberidx);
            int result = detailRepository.serchFollow(idx, memberidx);
            switch (result){
                case -1:
                    rs="에러";
                    break;
                case 0:
                    detailRepository.insertFollow(idx, memberidx);
                    rs = "팔로우 중";
                    break;
                case 1:
                    detailRepository.deleteFollow(idx, memberidx);
                    rs = "팔로우";
                    break;
            }
            model.addAttribute("follow", rs);
            model.addAttribute("boardIdx", board.getBoardIdx());
            return rs;

        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        model.addAttribute("boardIdx", board.getBoardIdx());
        return rs;
    }


}

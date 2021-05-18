package com.example.demo.mg.controller;

import com.example.demo.mg.domain.Board;
import com.example.demo.mg.domain.Comment;
import com.example.demo.mg.repository.DetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
    public String Commentprint(Model model, Comment comment, Board board)
    {
        List<Comment> commentList = null;
        try {
            commentList = detailRepository.Detailcommentselect(board);
            Board board1 = detailRepository.DetailBoardselect(board);
            int count = detailRepository.countLike(board);
            int result = detailRepository.serchFollow();
            String follow="";
            model.addAttribute("commentList",commentList);
            model.addAttribute("boardImage", board1.getBoardImage());
            model.addAttribute("boardAudio", board1.getBoardAudio());
            model.addAttribute("memberId",board1.getMemberId());
            model.addAttribute("boardDate",board1.getBoardDate());
            model.addAttribute("boardIdx", board1.getBoardIdx());
            model.addAttribute("likescore", count);
            if (result==1)
                follow="팔로우 중";
            else if(result==2)
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
    public ModelAndView commentrPost(Comment comment, Board board) {
        try {
            detailRepository.setComment(comment, board);
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return new ModelAndView("redirect:/detail?boardIdx="+board.getBoardIdx());
    }
    @PostMapping("like")
    @ResponseBody
    public int likePost(Model model, Comment comment, Board board) {
        try {
            int result = detailRepository.serchLike(board);
            switch (result){
                case -1:
                    break;
                case 0:
                    detailRepository.insertLike(board);
                    break;
                case 1:
                    detailRepository.deleteLike(board);
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
    public String followPost(Model model, Comment comment, Board board) {
        String rs="";
        try {
            int result = detailRepository.serchFollow();
            switch (result){
                case -1:
                    rs="에러";
                    break;
                case 0:
                    detailRepository.insertFollow();
                    rs = "팔로우 중";
                    break;
                case 1:
                    detailRepository.deleteFollow();
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

package com.example.demo.mg.controller;

import com.example.demo.ig.domain.Member;
import com.example.demo.mg.domain.Board;
import com.example.demo.mg.domain.Comment;
import com.example.demo.mg.repository.DetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
            commentList = detailRepository.Detailcommentselect(comment);
            Board board1 = detailRepository.DetailBoardselect(board);
            model.addAttribute("commentList",commentList);
            model.addAttribute("boardImage", board1.getBoardImage());
            model.addAttribute("boardAudio", board1.getBoardAudio());
            model.addAttribute("memberId",board1.getMemberId());
            model.addAttribute("boardDate",board1.getBoardDate());
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }

        return "detail";
    }

    @PostMapping("detail")
    public String commentrPost(Comment comment) {
        try {
            detailRepository.setComment(comment);
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return "detail";
    }

}

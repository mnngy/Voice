package com.example.demo.mg.controller;


import com.example.demo.mg.domain.Board;
import com.example.demo.mg.domain.Comment;
import com.example.demo.mg.domain.Like;
import com.example.demo.mg.repository.BoardRepository;
import com.example.demo.mg.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

@Controller
public class BoardController {

    @Autowired
    private final BoardRepository boardRepository;
    private final LikeService likeService;

    public BoardController(BoardRepository boardRepository, LikeService likeService) {
        this.boardRepository = boardRepository;
        this.likeService = likeService;
    }



    @GetMapping("main")
    public String boardPrint(Model model, Board board)
    {
        List<Board> boardList = null;
        boardList = likeService.LikeBoard();
        model.addAttribute("boardList", boardList);

        return "main";
    }
    @GetMapping("follow")
    public String followPrint(Model model, Board board, HttpServletRequest request)
    {
        String ID;
        HttpSession session = request.getSession();
        ID =(String) session.getAttribute("sessionMemberId");
        List<Board> boardList = null;
        boardList = likeService.LikeFollowBoard(ID);
        model.addAttribute("boardList", boardList);

        return "follow";
    }
    @PostMapping("likeList")
    @ResponseBody
    public int likePost(Model model, Comment comment, Board board, HttpServletRequest request, @RequestParam(value = "boardIdxval", required=false) String boardIdx) {
        String ID;
        HttpSession session = request.getSession();
        ID =(String) session.getAttribute("sessionMemberId");
        try {
            Long idx = boardRepository.MemberIDXselect(ID);
            Long boardIdxLong = Long.parseLong(boardIdx);
            int result = boardRepository.serchLike2(boardIdxLong, idx);
            switch (result){
                case -1:
                    break;
                case 0:
                    boardRepository.insertLike2(boardIdxLong, idx);
                    break;
                case 1:
                    boardRepository.deleteLike2(boardIdxLong, idx);
                    break;
            }
            int count = boardRepository.countLike2(boardIdxLong);
            model.addAttribute("likescore", count);
           // model.addAttribute("boardIdx", board.getBoardIdx());
            return count;

        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
       // model.addAttribute("boardIdx", board.getBoardIdx());
        return -1;
    }

}

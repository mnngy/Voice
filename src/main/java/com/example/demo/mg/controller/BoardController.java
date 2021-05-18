package com.example.demo.mg.controller;

import com.example.demo.mg.domain.Board;
import com.example.demo.mg.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.SQLException;
import java.util.List;

@Controller
public class BoardController {

    @Autowired
    private final BoardRepository boardRepository;

    public BoardController(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }


    @GetMapping("main")
    public String boardPrint(Model model, Board board)
    {
        List<Board> boardList = null;
        try {
            boardList = boardRepository.Boardselect(board);
            model.addAttribute("boardList", boardList);
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }

        return "main";
    }

}

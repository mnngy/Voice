package com.example.demo.ig.service;

import com.example.demo.ig.domain.Board;
import com.example.demo.ig.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service("ig.BoardService")
public class BoardService {

    private final BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public List<Board> findAllBoard() {
        List<Board> boards = null;
        try {
            boards = boardRepository.boardSelectAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return boards;
    }

    public List<Board> findBoards(Long memberIdx) {
        List<Board> boards = null;
        try {
            boards = boardRepository.boardSelectById(memberIdx);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return boards;
    }
}

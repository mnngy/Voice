package com.example.demo.ig.service;

import com.example.demo.ig.domain.Board;
import com.example.demo.ig.domain.Member;
import com.example.demo.ig.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@Service("ig.BoardService")
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;

    /**
     * 모든 게시글 리스트를 반환하는 서비스
     */
    public List<Board> findAllBoard() {
        List<Board> boards = null;
        try {
            boards = boardRepository.boardSelectAll();
        } catch (SQLException e) {
            log.error(this.getClass().getName() + "." + "findAllBoard" + " => " + e.getClass().getName() + ", " + " cause: " + e.getMessage());
        }
        return boards;
    }

    /**
     * 특정 회원의 게시글 리스트를 반환하는 서비스
     */
    public List<Board> findBoards(Long memberIdx) {
        List<Board> boards = null;
        try {
            boards = boardRepository.boardsSelectById(memberIdx);
        } catch (SQLException e) {
            log.error(this.getClass().getName() + "." + "findBoards" + " => " + e.getClass().getName() + ", " + " cause: " + e.getMessage());
        }
        return boards;
    }

    /**
     * 특정 게시글을 게시글 인덱스를 통해 찾는 서비스
     */
    public Board findBoard(Long boardIdx) {
        Board board = null;
        try {
            board = boardRepository.boardSelectById(boardIdx);
        } catch (SQLException e) {
            log.error(this.getClass().getName() + "." + "findBoard" + " => " + e.getClass().getName() + ", " + " cause: " + e.getMessage());
        }
        return board;
    }

    /**
     * 특정 게시글을 게시글 인덱스를 통해 삭제하는 서비스
     */
    public void deleteBoard(Long boardIdx, HttpServletResponse response) {
        PrintWriter out = null;
        response.setContentType("text/html;charset=euc-kr");

        try {
            int result = boardRepository.boardDeleteByIdx(boardIdx);

            if (result == 1) {
                out = response.getWriter();
                out.println("<script>" +
                        "alert('게시글을 삭제했습니다.'); location.href='/admin';" +
                        "</script>");
                out.close();
            } else {
                out = response.getWriter();
                out.println("<script>" +
                        "alert('게시글 삭제를 실패했습니다.'); location.href='/admin';" +
                        "</script>");
                out.close();
            }
        } catch (SQLException | IOException e) {
            log.error(this.getClass().getName() + "." + "deleteBoard" + " => " + e.getClass().getName() + ", " + " cause: " + e.getMessage());
        }
    }

    /**
     * 특정 게시글 정보 수정하는 서비스
     */
    public void editBoard(Board board, HttpServletResponse response) {
        PrintWriter out = null;
        response.setContentType("text/html;charset=euc-kr");
        int result = 0;

        try {
            result = boardRepository.boardUpdate(board);

            if (result == 1) {
                out = response.getWriter();
                out.println("<script>" +
                        "alert('게시글 정보를 수정했습니다.'); location.href='/admin';" +
                        "</script>");
                out.close();
            } else {
                out = response.getWriter();
                out.println("<script>" +
                        "alert('게시글 정보를 잘못 입력했습니다.'); location.href='/admin';" +
                        "</script>");
                out.close();
            }
        } catch (SQLException | IOException e) {
            log.error(this.getClass().getName() + "." + "editBoard" + " => " + e.getClass().getName() + ", " + " cause: " + e.getMessage());
        }
    }
}

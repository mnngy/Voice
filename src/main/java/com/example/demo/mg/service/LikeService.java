package com.example.demo.mg.service;

import com.example.demo.mg.domain.Board;
import com.example.demo.mg.domain.Like;
import com.example.demo.mg.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

@Service
public class LikeService {

    private final BoardRepository boardRepository;

    @Autowired
    public LikeService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public List<Board> LikeBoard(){
        List<Board> likeboardList= null;
        List<Like> likeList=null;
        try {
            likeboardList = boardRepository.Boardselect();
            likeList = boardRepository.serchLikeList();
            for (int i=0; i<likeboardList.size();i++){
                for(int j=0; j<likeList.size(); j++){
                    if(likeboardList.get(i).getBoardIdx()==likeList.get(j).getBoardIdx())
                        likeboardList.set(i,likeboardList.get(i)).setLikecount(likeList.get(j).getLikecount());
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return likeboardList;

    }
    public List<Board> LikeFollowBoard(String id){
        List<Board> likeboardList= null;
        List<Like> likeList=null;


        try {
            Long idx = boardRepository.MemberIDXselect(id);
            likeboardList = boardRepository.Followselect(idx);
            likeList = boardRepository.serchLikeList();
            for (int i=0; i<likeboardList.size();i++){
                for(int j=0; j<likeList.size(); j++){
                    if(likeboardList.get(i).getBoardIdx()==likeList.get(j).getBoardIdx())
                        likeboardList.set(i,likeboardList.get(i)).setLikecount(likeList.get(j).getLikecount());
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return likeboardList;

    }
}

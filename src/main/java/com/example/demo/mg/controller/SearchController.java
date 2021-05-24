package com.example.demo.mg.controller;

import com.example.demo.mg.domain.Search;
import com.example.demo.mg.repository.DetailRepository;
import com.example.demo.mg.repository.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.nio.channels.SeekableByteChannel;
import java.sql.SQLException;
import java.util.List;

@Controller
public class SearchController {

    @Autowired
    private final SearchRepository searchRepository;

    public SearchController(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }


    @GetMapping("search")
    public String searchpage(Model model)
    {
        return "search";
    }

    @PostMapping("search")
    public String searchfind(Model model, @RequestParam("searchtext") String searchtext)
    {
        List<Search> searchList = null;
        try {
            searchList = searchRepository.Searchselect(searchtext);
            model.addAttribute("searchList",searchList);
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return "search";
    }

}

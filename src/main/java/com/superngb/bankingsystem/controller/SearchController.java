package com.superngb.bankingsystem.controller;

import com.superngb.bankingsystem.domain.search.SearchInputBoundary;
import com.superngb.bankingsystem.model.FilterRequestModel;
import com.superngb.bankingsystem.model.ResponseModel;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
public class SearchController {

    private final SearchInputBoundary searchInputBoundary;

    public SearchController(SearchInputBoundary searchInputBoundary) {
        this.searchInputBoundary = searchInputBoundary;
    }

    @GetMapping
    public ResponseEntity<?> filter(@Valid @RequestBody FilterRequestModel model, Pageable pageable) {
        ResponseModel<?> responseModel = searchInputBoundary.filter(model, pageable);
        return new ResponseEntity<>(responseModel.getBody(), HttpStatus.valueOf(responseModel.getCode()));
    }
}

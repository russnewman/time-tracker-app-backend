package com.example.TimeTracker.dto;

import com.example.TimeTracker.model.Category;
import lombok.Getter;


@Getter
public class AddOrChangeCategoryRequest {
    private String url;
    private String host;
    private String category;
}

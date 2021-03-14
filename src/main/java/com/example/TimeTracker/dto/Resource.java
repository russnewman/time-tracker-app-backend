package com.example.TimeTracker.dto;

import com.example.TimeTracker.model.Category;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Resource {

    private Long employeeId;
    private String host;
    private String protocolIdentifier;
    private Category category;
    private HashMap<String, String> urlToTabName;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime startTime;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime endTime;


    private int activity;
    private long duration;

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof Resource)) return false;
//        Resource resource = (Resource) o;
//        return Objects.equals(getHost(), resource.getHost()) &&
//                Objects.equals(getEmployeeId(), resource.getEmployeeId()) &&
//                getCategory() == resource.getCategory();
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(getHost(), getEmployeeId(), getCategory());
//    }
}

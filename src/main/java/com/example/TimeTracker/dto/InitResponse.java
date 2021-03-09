package com.example.TimeTracker.dto;
import com.example.TimeTracker.dto.PersonInfo;
import com.example.TimeTracker.model.Person;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class InitResponse {
    @JsonProperty("userInfo")
    private PersonInfo personInfo;

//    @JsonProperty("employees")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<PersonInfo> employees;

    @JsonInclude(JsonInclude.Include.NON_NULL)  
//    @JsonProperty("managers")
    private List<PersonInfo> managers;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private PersonInfo userManager;
}

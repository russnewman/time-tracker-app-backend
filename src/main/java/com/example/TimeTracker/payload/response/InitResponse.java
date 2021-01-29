package com.example.TimeTracker.payload.response;
import com.example.TimeTracker.model.Person;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class InitResponse {
    @JsonProperty("userInfo")
    private UserInfoResponse userInfoResponse;
    @JsonProperty("userEmployees")
    private List<Person> employees;
//    private UserEmployeesResponse userEmployeesResponse;
}

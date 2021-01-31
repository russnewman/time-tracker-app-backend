package com.example.TimeTracker.payload.response;
import com.example.TimeTracker.model.Manager;
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
    private UserInfoResponse userInfoResponse;
    @JsonProperty("userEmployees")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Person> employees;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("managers")
    private List<Manager> managers;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Person userManager;
}

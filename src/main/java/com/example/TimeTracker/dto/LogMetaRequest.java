package com.example.TimeTracker.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@ToString
public class LogMetaRequest {

    @Getter
    @Setter
    @ToString
    public static class Meta {
        @NotBlank
        String name;

        @NotBlank
        String property;

        @NotBlank
        String content;
    }

    @NotBlank
    private Long logId;

    @NotBlank
    private List<Meta> metas;

}

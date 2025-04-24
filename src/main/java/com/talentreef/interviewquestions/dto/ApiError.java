package com.talentreef.interviewquestions.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class ApiError {
    private int status;
    private LocalDateTime timestamp;
    private List<String> errors;
}

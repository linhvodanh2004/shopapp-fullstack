package com.project.shopapp.responses;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class ApiResponse<T>{
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
    private int status;
    private String message;
    private T data;
    private List<String> errors;
}

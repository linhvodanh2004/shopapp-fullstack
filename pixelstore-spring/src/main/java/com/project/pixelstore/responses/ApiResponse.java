package com.project.pixelstore.responses;

import lombok.*;
import org.springframework.http.HttpStatus;

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
    private boolean success;
    private String message;
    private T data;
    private int status;
    private String error;

    public static ApiResponse<Object> ok(Object data){
        return ApiResponse.builder()
                .success(true)
                .data(data)
                .status(HttpStatus.OK.value())
                .message("OK")
                .build();
    }
//    public static ApiResponse<Object> badRequest(List<String> errors){
//        return ApiResponse.builder()
//                .status(HttpStatus.BAD_REQUEST.value())
//                .message("BAD REQUEST")
//                .errors(errors)
//                .build();
//    }
}

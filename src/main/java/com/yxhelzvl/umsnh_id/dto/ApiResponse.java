package com.yxhelzvl.umsnh_id.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ApiResponse<T> {
    private final LocalDateTime timestamp;
    private final int status;
    private final boolean success;
    private final String message;
    private final T data;
    private final List<String> errors;

    private ApiResponse(ApiResponseBuilder<T> builder) {
        this.timestamp = LocalDateTime.now();
        this.status = builder.status;
        this.success = HttpStatus.valueOf(builder.status).is2xxSuccessful();
        this.message = builder.message;
        this.data = builder.data;
        this.errors = builder.errors;
    }

    // Getters...

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponseBuilder<T>()
                .status(HttpStatus.OK.value())
                .message(message)
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> created(String message, T data) {
        return new ApiResponseBuilder<T>()
                .status(HttpStatus.CREATED.value())
                .message(message)
                .data(data)
                .build();
    }
    public static <T> ApiResponse<T> error(HttpStatus status, String message, List<String> errors) {
        return new ApiResponseBuilder<T>()
                .status(status.value())
                .message(message)
                .errors(errors)
                .build();
    }

    public static class ApiResponseBuilder<T> {
        private int status;
        private String message;
        private T data;
        private List<String> errors = new ArrayList<>();

        public ApiResponseBuilder<T> status(int status) {
            this.status = status;
            return this;
        }

        public ApiResponseBuilder<T> message(String message) {
            this.message = message;
            return this;
        }

        public ApiResponseBuilder<T> data(T data) {
            this.data = data;
            return this;
        }

        public ApiResponseBuilder<T> errors(List<String> errors) {
            this.errors = errors;
            return this;
        }

        public ApiResponse<T> build() {
            return new ApiResponse<>(this);
        }
    }
}
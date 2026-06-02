package com.portfolio.tasks.dto;

import com.portfolio.tasks.model.Task;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

public class TaskDto {

    @Data
    public static class Request {
        @NotBlank(message = "El título es obligatorio")
        private String title;
        private String description;
        private Task.Status status;
    }

    @Data
    public static class Response {
        private Long id;
        private String title;
        private String description;
        private Task.Status status;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public static Response from(Task task) {
            Response dto = new Response();
            dto.setId(task.getId());
            dto.setTitle(task.getTitle());
            dto.setDescription(task.getDescription());
            dto.setStatus(task.getStatus());
            dto.setCreatedAt(task.getCreatedAt());
            dto.setUpdatedAt(task.getUpdatedAt());
            return dto;
        }
    }
}

package com.example.simple.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "todos")
public class TodoDTO  {
    @Id
    private String id;

    @NotNull(message = "Todo is required and cannot be null")
    private String todo;

    @NotNull(message = "Description is required and cannot be null")
    private String description;

    @NotNull(message = "Status is required and cannot be null")
    private Boolean completed;

    private Date createdAt;
    private Date updatedAt;
}

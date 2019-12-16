package ru.itpark.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    private int id;
    private Status status;
    private String phrase;
}

enum Status {
    Waiting,
    Running,
    Completed,
    Canceled
}

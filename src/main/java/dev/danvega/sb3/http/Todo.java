package dev.danvega.sb3.http;

public record Todo(
    Integer userId,
    Integer id,
    String title,
    Boolean completed
) {}
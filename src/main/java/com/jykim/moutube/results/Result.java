package com.jykim.moutube.results;

public interface Result {
    String NAME = "result";
    String name();

    default String nameToLower() {
        return this.name().toLowerCase();
    }
}
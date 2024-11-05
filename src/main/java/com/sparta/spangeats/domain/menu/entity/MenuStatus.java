package com.sparta.spangeats.domain.menu.entity;

public enum MenuStatus {
    ACTIVE("활성"),
    INACTIVE("비활성"),
    DELETED("삭제됨");

    private final String description;

    MenuStatus(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}

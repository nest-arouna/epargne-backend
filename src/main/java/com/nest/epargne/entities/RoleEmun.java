package com.nest.epargne.entities;

public enum RoleEmun {
    ADMIN("ADMIN"),
    USER("USER");
    public final String label;

    private RoleEmun(String label) {
        this.label = label;
    }
}

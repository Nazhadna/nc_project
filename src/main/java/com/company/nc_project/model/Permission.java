package com.company.nc_project.model;

public enum Permission {
    READ("all"),
    WRITE("for_user"),
    UPDATE("for_admin");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}

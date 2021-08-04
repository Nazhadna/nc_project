package com.company.nc_project.model;

public enum Permission {
    ALL("all"),
    USER("for_user"),
    ADMIN("for_admin");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}

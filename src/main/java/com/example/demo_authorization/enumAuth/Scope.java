package com.example.demo_authorization.enumAuth;

public enum Scope {
    image("image"),
    product("product"),
    user("user");

    public final String label;

    private Scope(String label) {
        this.label = label;
    }
}

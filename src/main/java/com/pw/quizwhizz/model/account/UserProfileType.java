package com.pw.quizwhizz.model.account;

import lombok.Getter;
import lombok.ToString;

@ToString

public enum UserProfileType {
    ROLE_USER("ROLE_USER"),
    ROLE_ADMIN("ROLE_ADMIN");

    private String roleName;

    UserProfileType(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}

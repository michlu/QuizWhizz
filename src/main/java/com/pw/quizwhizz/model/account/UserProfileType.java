package com.pw.quizwhizz.model.account;

import lombok.ToString;

/**
 * Enum dostepnych ról dla uzytkownikow.
 * @author Michał Nowiński
 */
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

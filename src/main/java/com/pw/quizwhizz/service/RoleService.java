package com.pw.quizwhizz.service;

import com.pw.quizwhizz.model.account.Role;

import java.util.List;

public interface RoleService {
    List<Role> findAll();
    Role findById(Long id);
}

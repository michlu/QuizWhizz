package com.pw.quizwhizz.service;

import com.pw.quizwhizz.model.account.Role;

import java.util.List;

/**
 * Abstrakcyjna warstwa serwisu
 * @author Michał Nowiński
 */
public interface RoleService {
    List<Role> findAll();
    Role findById(Long id);
}

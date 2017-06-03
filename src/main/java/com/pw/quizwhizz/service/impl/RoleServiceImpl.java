package com.pw.quizwhizz.service.impl;

import com.pw.quizwhizz.model.account.Role;
import com.pw.quizwhizz.repository.RoleRepository;
import com.pw.quizwhizz.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Serwis domenowy udostepniajacy funkcjonalnosci dla domeny Role
 * @author Michał Nowiński
 * @see RoleService
 */
@Service
public class RoleServiceImpl implements RoleService {

    RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role findById(Long id) {
        return roleRepository.findOne(id);
    }
}

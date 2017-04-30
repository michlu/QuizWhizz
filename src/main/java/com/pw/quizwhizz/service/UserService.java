package com.pw.quizwhizz.service;

import com.pw.quizwhizz.model.player.Player;
import com.pw.quizwhizz.model.account.User;

import java.util.List;

public interface UserService {
    User findById(Long Id);
    void addWithDefaultRole(User user);
    void addWithAdminRole(User user);
    User findByEmail(String email);
    List<User> findAll();
    void deleteById(Long Id);
    void addRoleToUser(String user, String[] role);
    void removeRoleUser(String userId, String[] roles);
    void update(User user);
    Player convertToPlayer(User user);

}

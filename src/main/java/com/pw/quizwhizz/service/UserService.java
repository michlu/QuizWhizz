package com.pw.quizwhizz.service;

import com.pw.quizwhizz.model.dto.Ranking;
import com.pw.quizwhizz.model.dto.UserAllStats;
import com.pw.quizwhizz.model.game.Player;
import com.pw.quizwhizz.model.account.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    void updateUserWithImage(User user, MultipartFile file, String saveDirectory) throws IOException;
    Player findPlayerByUserId(Long userId);
    List<UserAllStats> findAllScoreForUser(Long userId);
    List<Ranking> findGeneralRank(int limitSearch);
}

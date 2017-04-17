package com.pw.quizwhizz.repository;

import com.pw.quizwhizz.model.account.Role;
import com.pw.quizwhizz.model.account.UserProfileType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Role findByRole(UserProfileType role);
}

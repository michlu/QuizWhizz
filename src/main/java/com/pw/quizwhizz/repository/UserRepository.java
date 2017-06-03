package com.pw.quizwhizz.repository;

import com.pw.quizwhizz.model.account.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	User findByEmail(String email);
	User findById(Long id);
	void deleteById(Long id);
	@Query(value = "SELECT count(u) FROM User u")
	int countAll();
}

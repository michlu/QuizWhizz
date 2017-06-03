package com.pw.quizwhizz.repository;

import com.pw.quizwhizz.model.account.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repozytorium udostepnia Uzytkownikow
 * @author Michał Nowiński
 * @see JpaRepository
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	User findByEmail(String email);
	User findById(Long id);
	void deleteById(Long id);

	/** @return zwraca ilosc zarejestrowanych uzytkownikow */
	@Query(value = "SELECT count(u) FROM User u")
	int countAll();
}

package com.pw.quizwhizz.model.account;

import lombok.Data;

import javax.persistence.*;

/**
 * Tabela ról dostepnych dla uzytkwnikow portalu. Zawiera id roli.
 * @author Michał Nowiński
 */
@Entity
@Data
@Table(name = "role")
public class Role {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;

	/** Radzaje ról wyrazone sa w typie enum  {@link UserProfileType} */
    @Enumerated(EnumType.STRING)
	private UserProfileType role;
}
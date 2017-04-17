package com.pw.quizwhizz.model.account;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "role")
public class Role {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;

    @Enumerated(EnumType.STRING)
	private UserProfileType role;

}
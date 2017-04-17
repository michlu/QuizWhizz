package com.pw.quizwhizz.model.account;

import com.pw.quizwhizz.annotation.UniqueEmail;
import com.pw.quizwhizz.annotation.ValidEmail;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Entity
@Data
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	@Column(name = "first_name", nullable = false)
	private String firstName;

	@NotEmpty
	@Column(name = "password", nullable = false)
	private String password;

    @UniqueEmail
	@NotEmpty
	@ValidEmail
	@Column(name = "user_email", nullable = false, unique = true)
	private String email;

	@Column(name = "date_registration", nullable = false)
	@Temporal(TemporalType.DATE) // konwertuje util.Date na sql.Date
	private Date regDate = new Date();

	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinTable(
			name = "user_role",
			joinColumns = { @JoinColumn(name = "user_id") },
			inverseJoinColumns = { @JoinColumn(name = "role_id")})
	private Set<Role> roles = new HashSet<>();

	public void addRole(Role role) {
		if(! roles.contains(role))
			roles.add(role);
	}
	public void removeRole(Role role) {
		if(roles.contains(role))
			roles.remove(role);
	}
}
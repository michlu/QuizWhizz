package com.pw.quizwhizz.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user")
public class User implements Serializable {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "user_login", nullable = false, unique = true)
    private String userLogin;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "user_mail", nullable = false, unique = true)
    private String mail;
    @Column(name = "date_registration", nullable = false)
    @Temporal(TemporalType.DATE) // konwertuje util.Date na sql.Date
    private java.util.Date regDate;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_role",
            joinColumns = { @JoinColumn(name = "user_login", referencedColumnName = "user_login") },
            inverseJoinColumns = { @JoinColumn(name = "role_name", referencedColumnName = "role_name") })
    List<Role> role;

    public User() {
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRole() {
        return role;
    }

    public void setRole(List<Role> role) {
        this.role = role;
    }

    public void addRole(Role addRole){
        if(null == role)
            role = new ArrayList<Role>();
        role.add(addRole);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userLogin='" + userLogin + '\'' +
                ", firstName='" + firstName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        return role != null ? role.equals(user.role) : user.role == null;
    }

}

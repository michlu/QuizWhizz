package com.pw.quizwhizz.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "role")
public class Role implements Serializable {
    @Id
    @Column(name = "id_role")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "role_name", nullable = false)
    private String roleName;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_role",
            inverseJoinColumns = { @JoinColumn(name = "user_login", referencedColumnName = "user_login") },
            joinColumns = { @JoinColumn(name = "role_name", referencedColumnName = "role_name") })
    List<User> user;

    public Role() {
    }

    private Role(String roleName) {
        this.roleName = roleName;
    }

    public static Role user(){
        return new Role("user");
    }

    public static Role admin(){
        return new Role("admin");
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", roleName=" + roleName +
                '}';
    }
}

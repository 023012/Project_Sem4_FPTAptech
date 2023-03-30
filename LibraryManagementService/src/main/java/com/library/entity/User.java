package com.library.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String name;

    @NonNull
    @Column(name = "user_name", length = 100)
    private String username;

    private String password;

    @Column(unique = true)
    private String email;

    private String phoneNumber;

    private String address;

    private String avatar;

    private int virtualWallet;

    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    public enum AccountStatus{
        ACTIVE ,
        CLOSED,
        CANCELED,
        BLACKLISTED,
        NONE
    }

    public User(String name, @NonNull String username, String password, String email, String phoneNumber, String address, String avatar, int virtualWallet, AccountStatus status, Collection<Role> roles) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.avatar = avatar;
        this.virtualWallet = virtualWallet;
        this.status = status;
        this.roles = roles;
    }

    /*
     * FetchType.EAGER: When you load User table from database, it will automatically load Role table as well
     * FetchType.LAZY : When you load User table from database, it just loads User from DB, you have to call getAllRoles()
     * from User to get their roles
     * */

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();
}

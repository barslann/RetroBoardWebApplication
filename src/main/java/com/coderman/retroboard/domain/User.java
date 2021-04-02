package com.coderman.retroboard.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rb_user")
@Data
@AllArgsConstructor // generate a constructor with the id,username,password and role
@NoArgsConstructor // default constructor
public class User {
    @Id // identity field of the entity
    @GeneratedValue // auto generated value
    private Long id;

    private String username;
    private String password;
    private String role;
}

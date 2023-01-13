package com.gracegh.springsecurityclient.Entity;

import lombok.Data;

import javax.persistence.*;

@Entity//this will enable the table to connect to the database...
@Data//automating the creation of getters and setters...
@Table(name = "tbl_user")
public class User {

    //making the id field an id...
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    @Column(length = 60)
    private String password;

    private String role;
    private boolean enabled = false;
}

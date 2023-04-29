package com.grit.learning.model;

import javax.persistence.*;

@Entity
@Table(name = "USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "first_name", nullable = true)
    private String firstName;
    @Column(name = "last_name",nullable = true)
    private String lastName;

    @Column(name = "email",nullable = false)
    private String email;
    @Column(name="phone")
    private String phone;
    @Column(name = "age")
    private String age;
    @Column(name = "education_type")
    private String educationType;
    @Column(name = "password",nullable = false)
    private String password;
    @Column(name="role",nullable = false)
    private String role;

    public User( String firstName, String lastName, String email, String phone, String age, String educationType, String password,String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.age = age;
        this.educationType = educationType;
        this.password = password;
        this.role = role;
    }

    public User(){}

    public Integer getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAge() {
        return age;
    }

    public String getEducationType() {
        return educationType;
    }

    public String getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setEducationType(String educationType) {
        this.educationType = educationType;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

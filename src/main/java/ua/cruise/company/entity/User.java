package ua.cruise.company.entity;

import ua.cruise.company.enums.UserRole;

public class User {
    private Long id;
    private String email; //this is our login
    private String password;
    private String firstNameEn;
    private String lastNameEn;
    private String firstNameNative;
    private String lastNameNative;
    private UserRole role;

    public User() {
    }

    public User(Long id, String email, String password, String firstNameEn, String lastNameEn, String firstNameNative, String lastNameNative, UserRole role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstNameEn = firstNameEn;
        this.lastNameEn = lastNameEn;
        this.firstNameNative = firstNameNative;
        this.lastNameNative = lastNameNative;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstNameEn() {
        return firstNameEn;
    }

    public void setFirstNameEn(String firstNameEn) {
        this.firstNameEn = firstNameEn;
    }

    public String getLastNameEn() {
        return lastNameEn;
    }

    public void setLastNameEn(String lastNameEn) {
        this.lastNameEn = lastNameEn;
    }

    public String getFirstNameNative() {
        return firstNameNative;
    }

    public void setFirstNameNative(String firstNameNative) {
        this.firstNameNative = firstNameNative;
    }

    public String getLastNameNative() {
        return lastNameNative;
    }

    public void setLastNameNative(String lastNameNative) {
        this.lastNameNative = lastNameNative;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstNameEn='" + firstNameEn + '\'' +
                ", lastNameEn='" + lastNameEn + '\'' +
                ", firstNameNative='" + firstNameNative + '\'' +
                ", lastNameNative='" + lastNameNative + '\'' +
                ", role=" + role +
                '}';
    }
}

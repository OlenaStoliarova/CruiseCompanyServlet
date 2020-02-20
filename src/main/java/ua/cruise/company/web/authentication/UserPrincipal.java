package ua.cruise.company.web.authentication;

import ua.cruise.company.enums.UserRole;

public class UserPrincipal {
    private Long id;
    private String email; //this is our login
    private UserRole role;

    public UserPrincipal() {
    }

    public UserPrincipal(String email) {
        this.email = email;
    }

    public UserPrincipal(Long id, String email, UserRole role) {
        this.id = id;
        this.email = email;
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

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserPrincipal{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }
}

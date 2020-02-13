package ua.training.cruise_company_servlet.web.authentication;

import ua.training.cruise_company_servlet.enums.UserRole;

class UserPrincipal{
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public UserRole getRole() {
        return role;
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

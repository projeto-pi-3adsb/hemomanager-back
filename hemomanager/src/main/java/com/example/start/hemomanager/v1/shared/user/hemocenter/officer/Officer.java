package com.example.start.hemomanager.v1.shared.user.hemocenter.officer;
import com.example.start.hemomanager.v1.shared.user.User;
import org.hibernate.id.GUIDGenerator;

import javax.persistence.Entity;
import java.util.UUID;

//@Entity
public class Officer extends User {
    private String roleOfficer;
    private boolean admin;

    public Officer() {
    }

    public Officer(Integer uuid, String name, String cpfCnpj, String email, String password, boolean logged, boolean admin, String roleOfficer) {
        super(uuid, name, cpfCnpj, email, password, logged);
        this.admin = admin;
        this.roleOfficer = roleOfficer;
    }

    public boolean authenticateOfficer(String email, String password){
        boolean auth = email.equals(super.getEmail()) && password.equals(super.getPassword());
        setLogged(auth);
        return auth;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    @Override
    public boolean doLogin() {
        return false;
    }

    @Override
    public boolean doLogout() {
        return false;
    }
}
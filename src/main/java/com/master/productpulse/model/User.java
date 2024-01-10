package com.master.productpulse.model;

import com.master.productpulse.enums.UserType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
@NoArgsConstructor
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String username;

    private String email;

    private String phone;

    private String password;

    private Boolean firstLogin;

    private LocalDateTime addedDate ;

    private LocalDateTime passwordSetDate;

    private String userType;

    public User(String name, String username, String email, String phone, String password, Boolean firstLogin, LocalDateTime passwordSetDate, String userType, LocalDateTime addedDate) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.firstLogin = firstLogin;
        this.passwordSetDate = passwordSetDate;
        this.userType = userType;
        this.addedDate = addedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(username, user.username) && Objects.equals(email, user.email) && Objects.equals(phone, user.phone) && Objects.equals(password, user.password) && Objects.equals(firstLogin, user.firstLogin) && Objects.equals(addedDate, user.addedDate) && Objects.equals(passwordSetDate, user.passwordSetDate) && Objects.equals(userType, user.userType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, username, email, phone, password, firstLogin, addedDate, passwordSetDate, userType);
    }
}

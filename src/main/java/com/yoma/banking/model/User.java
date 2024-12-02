package com.yoma.banking.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @Column(name = "UserID", nullable = false, updatable = false)
    private String userId;

    @Column(name = "UserName", nullable = false, length = 30)
    private String userName;

    @Column(name = "Email", nullable = false, unique = true, length = 30)
    private String email;

    @Column(name = "Password", nullable = false)
    private String password;

    @Column(name = "CreatedDate", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "UpdatedDate")
    private LocalDateTime updatedDate;
}

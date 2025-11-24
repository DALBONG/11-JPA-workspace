package com.example.finalTest.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TB_Company")
@Data
public class Company {

    @Id @GeneratedValue
    @Column(name = "company_id")
    private Long id;

    @Column(name = "company_name", nullable = false, length = 100)
    private String name;

    @Column(name = "company_login_id", nullable = false, length = 100, unique = true)
    private String loginId;

    @Column(name = "company_login_pwd", nullable = false, length = 40)
    private String loginPwd;

    @CreationTimestamp
    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column(name = "updated_date", nullable = false)
    private LocalDateTime updatedDate;

}

package com.example.demo_authorization.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="authorizations")
public class Authorization {
    @Id
    private String code;
    private String scope;
    private long userId;
    private String redirectUrl;
    private long clientId;
}

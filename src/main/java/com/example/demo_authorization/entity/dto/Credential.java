package com.example.demo_authorization.entity.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
//@Entity
//@Table(name="credentials")
public class Credential {
    private String accessToken;
}

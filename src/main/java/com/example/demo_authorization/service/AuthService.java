package com.example.demo_authorization.service;

import com.example.demo_authorization.entity.Authorization;
import com.example.demo_authorization.entity.User;
import com.example.demo_authorization.entity.dto.Credential;
import com.example.demo_authorization.enumAuth.Scope;
import com.example.demo_authorization.repository.AuthorizationRepository;
import com.example.demo_authorization.repository.UserRepository;
import com.example.demo_authorization.util.JwtUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Log4j2
public class AuthService {
    @Autowired
    private AuthorizationRepository authorizationRepository;
    @Autowired
    private UserRepository userRepository;

    // kiểm tra đăng nhập
    public Optional<Authorization> login(User user,Integer clientId, String scope, String redirectUrl) {
        User userByName = userRepository.findByUsername(user.getUsername()).orElse(null);
        Authorization authorization = new Authorization();

        if (userByName != null && user.getPassword().equals(userByName.getPassword())) {
            authorization.setCode(UUID.randomUUID().toString());
            authorization.setScope(scope);
            authorization.setUserId(userByName.getId());
            authorization.setClientId(clientId);
            authorization.setRedirectUrl(redirectUrl);
            authorizationRepository.save(authorization);
            return Optional.ofNullable(authorization);
        } else {
            return Optional.empty();
        }
    }

    public Optional<Credential> checkAuthorizationCode(String authCode, String redirectUrl, Integer client_id) {

        Authorization authorization = authorizationRepository.findById(authCode).orElse(null);

        if (authorization != null) {
            if (client_id!= null && client_id == authorization.getUserId()){

                Credential credential = new Credential();
                String redirectUrlCheck = authorization.getRedirectUrl();
                if(redirectUrlCheck != null && redirectUrlCheck.equals(redirectUrl)){
                    String accessToken = JwtUtil.generateToken(authorization.getClientId(),authorization.getUserId() , authorization.getScope(), JwtUtil.ONE_DAY * 7);
                    credential.setAccessToken(accessToken);
                    return Optional.ofNullable(credential);
                }
            }
        }
        return Optional.empty();
    }
}

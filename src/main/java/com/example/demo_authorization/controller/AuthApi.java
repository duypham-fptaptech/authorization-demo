package com.example.demo_authorization.controller;

import com.example.demo_authorization.entity.User;
import com.example.demo_authorization.service.AuthService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Log4j2
@RestController
@RequestMapping(path = "api/v1")
@CrossOrigin(origins = "*")
public class AuthApi {
    @Autowired
    private AuthService authService;

    @RequestMapping(path = "/login",method = RequestMethod.POST)
    public ResponseEntity<?> login(
            @RequestParam(name = "clientId",required = false) Integer clientId,
            @RequestParam(name = "scope",required = false) String scope,
            @RequestParam(name = "redirectUrl",required = false) String redirectUrl,
            @RequestBody User user
    ) throws IOException {

        if (clientId == null){
            return new ResponseEntity(
                    "clientId khong ton tai",
                    HttpStatus.BAD_REQUEST);
        }

        if (scope == null){
            return new ResponseEntity(
                    "scope khong ton tai",
                    HttpStatus.BAD_REQUEST);
        }

        if (redirectUrl == null){
            return new ResponseEntity(
                    "redirectUrl khong ton tai",
                    HttpStatus.BAD_REQUEST);
        }

        if (!authService.login(user,clientId,scope,redirectUrl).isPresent()){
            return new ResponseEntity(
                    "sai mat khau hoac ko ton tai ",
                    HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(
                authService.login(user,clientId,scope,redirectUrl),
                HttpStatus.OK);
    }

    @RequestMapping(path = "/auth",method = RequestMethod.POST)
    public ResponseEntity<?> auth(
            @RequestParam(name = "authCode") String authCode,
            @RequestParam(name = "redirectUrl",required = false) String redirectUrl,
            @RequestParam(name = "client_id",required = false) Integer client_id
    ) throws IOException {

//        log.info("redirectUrl =" + redirectUrl);
        if (redirectUrl == null){
            return new ResponseEntity(
                    "redirectUrl khong ton tai",
                    HttpStatus.BAD_REQUEST);
        }
//        log.info("client_id =" + client_id);
        if (client_id == null){
            return new ResponseEntity(
                    "client_id khong ton tai",
                    HttpStatus.BAD_REQUEST);
        }

        if (!authService.checkAuthorizationCode(authCode,redirectUrl,client_id).isPresent()){
            return new ResponseEntity(
                    "auth khong ton tai hoac sai redirectUrl",
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(
                authService.checkAuthorizationCode(authCode,redirectUrl,client_id),
                HttpStatus.OK);
    }
}

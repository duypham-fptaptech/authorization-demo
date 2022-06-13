package com.example.demo_authorization.util;

import java.util.Date;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtUtil {
    private static Algorithm algorithm;
    private static JWTVerifier verifier;
    /*this is jwt secret key use to encode jwt token only backend server hold this key
     * if an attacker know this key his can modify jwt token in the right way to grant access to api
     */
    private static final String JWT_SECRET_KEY = "secret";
    //some time units constant
    public static final int ONE_SECOND = 1000;
    public static final int ONE_MINUTE = ONE_SECOND * 60;
    public static final int ONE_HOUR = ONE_MINUTE * 60;
    public static final int ONE_DAY = ONE_HOUR * 24;
    public static final String ROLE_CLAIM_KEY = "role";
    public static final String ClientId_CLAIM_KEY = "clientId";
    public static final String UserId_CLAIM_KEY = "userId";

    public static Algorithm getAlgorithm() {
        if (algorithm == null) {
            algorithm = Algorithm.HMAC256(JWT_SECRET_KEY.getBytes());
        }
        return algorithm;
    }

    public static JWTVerifier getVerifier() {
        if(verifier == null) {
            verifier = JWT.require(getAlgorithm()).build();
        }
        return verifier;
    }

    public static DecodedJWT getDecodedJwt(String token){
        DecodedJWT decodedJWT = getVerifier().verify(token);
        return decodedJWT;
    }

    public static String generateToken(Long clientId, Long userId, String role, int expireAfter) {
        if(role == null || role.length() == 0) {
            return JWT.create()
                    .withClaim(JwtUtil.ClientId_CLAIM_KEY,clientId)
                    .withClaim(JwtUtil.UserId_CLAIM_KEY,userId)
//                    .withSubject(subject)
                    .withExpiresAt(new Date(System.currentTimeMillis() + expireAfter))
//                    .withIssuer(issuer)
                    .sign(getAlgorithm());
        }
        return JWT.create()
                .withClaim(JwtUtil.ClientId_CLAIM_KEY,clientId)
                .withClaim(JwtUtil.UserId_CLAIM_KEY,userId)
//                .withSubject(subject)
                .withExpiresAt(new Date(System.currentTimeMillis() + expireAfter))
//                .withIssuer(issuer)
                // when role n -> n user
                .withClaim(JwtUtil.ROLE_CLAIM_KEY, role)
                //when role n -> 1 user
//                .withClaim(JwtUtil.ROLE_CLAIM_KEY, role) //get first role in Authorities
                .sign(getAlgorithm());
    }
}

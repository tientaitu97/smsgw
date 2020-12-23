package vn.vnpay.service;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    private final static Logger LOG = LogManager.getLogger(JwtService.class);
    public static final String USERNAME = "username";
    public static final String SECRET_KEY = "11111111111111111111111111111111";
    public static final int EXPIRE_TIME = 86400000;

    public String generateTokenLogin(String username) {
        String token = null;
        try {
            LOG.info("begin generate token with username: {}", username);
            JWSSigner signer = new MACSigner(generateShareSecret());
            JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder();
            builder.claim(USERNAME, username);
            builder.expirationTime(generateExpirationDate());
            JWTClaimsSet claimsSet = builder.build();
            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
            signedJWT.sign(signer);
            token = signedJWT.serialize();
            LOG.info("end generate token: {}", token);
        } catch (Exception e) {
            LOG.error("Error generate token: ", e);
        }
        return token;
    }

    private JWTClaimsSet getClaimsFromToken(String token) {
        JWTClaimsSet claims = null;
        try {
            LOG.info("begin get claims with token: {}", token);
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWSVerifier verifier = new MACVerifier(generateShareSecret());
            if (signedJWT.verify(verifier)) {
                claims = signedJWT.getJWTClaimsSet();
            }
            LOG.info("end get claims with token: {}", token);
        } catch (Exception e) {
            LOG.error("Error getClaimsFromToken: ", e);
        }
        return claims;
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + EXPIRE_TIME);
    }

    private Date getExpirationDateFromToken(String token) {
        JWTClaimsSet claims = getClaimsFromToken(token);
        return claims.getExpirationTime();
    }

    public String getUsernameFromToken(String token) {
        String username = null;
        try {
            LOG.info("begin get username from token: {}", token);
            JWTClaimsSet claims = getClaimsFromToken(token);
            username = claims.getStringClaim(USERNAME);
            LOG.info("get username success: {}", username);
        } catch (Exception e) {
            LOG.error("Error getUsernameFromToken: ", e);
        }
        return username;
    }

    private byte[] generateShareSecret() {
        return SECRET_KEY.getBytes();
    }

    private Boolean isTokenExpired(String token) {
        Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Boolean validateTokenLogin(String token) {
        if (token == null || token.trim().length() == 0) {
            LOG.warn("token is null or empty.");
            return false;
        }
        String username = getUsernameFromToken(token);
        if (username == null || username.isEmpty()) {
            LOG.warn("username is null or empty.");
            return false;
        }
        if (isTokenExpired(token)) {
            LOG.warn("token is expired");
            return false;
        }
        return true;
    }
}

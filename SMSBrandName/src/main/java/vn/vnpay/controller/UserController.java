package vn.vnpay.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import vn.vnpay.bean.ResponseCustom;
import vn.vnpay.bean.UserRequest;
import vn.vnpay.bean.UserResponse;
import vn.vnpay.service.JwtService;
import vn.vnpay.service.UserService;
import vn.vnpay.until.LogCommon;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/smsgw")
public class UserController {
    private final static Logger LOG = LogManager.getLogger(UserController.class);

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;

    @PostMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody UserRequest request, HttpServletRequest http) {
        ThreadContext.put(LogCommon.TOKEN, UUID.randomUUID().toString().replaceAll("-", ""));
        UserResponse userResponse = UserResponse.builder().build();
        ResponseCustom responseCustom = userService.checkLogin(request);
        if (responseCustom.equals(ResponseCustom.SUCCESS)) {
            userResponse.setToken(jwtService.generateTokenLogin(request.getUsername()));
        }
        userResponse.setCode(responseCustom.getCode());
        userResponse.setDescription(responseCustom.getDescription());
        return ResponseEntity.ok(userResponse);

    }


    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<?> getAllUser() {
        LOG.info("RUN GET");
        return ResponseEntity.ok("OK");
    }

    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        // Don't do this in production, use a proper list  of allowed origins
        config.setAllowedOrigins(Collections.singletonList("*"));
        config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH"));
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}

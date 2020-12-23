package vn.vnpay.filter;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import vn.vnpay.bean.ResponseCustom;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public final class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.setStatus(ResponseCustom.UNAUTHORIZED.getCode());
        response.getWriter().write(ResponseCustom.UNAUTHORIZED.getDescription());
    }
}

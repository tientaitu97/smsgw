package vn.vnpay.filter;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import vn.vnpay.bean.ResponseCustom;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exc)
            throws IOException {
        response.setStatus(ResponseCustom.FORBIDDEN.getCode());
        response.getWriter().write(ResponseCustom.FORBIDDEN.getDescription());
    }
}
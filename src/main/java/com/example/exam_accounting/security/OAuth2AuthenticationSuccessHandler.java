package com.example.exam_accounting.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

   @Autowired
   @Lazy
   private JwtTokenProvider tokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        // 1. Lấy thông tin đối tượng user đăng nhập thành công từ Spring Security
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

       String token = tokenProvider.createToken(authentication);


        // 3. Xây dựng URL chuyển hướng về lại Frontend (địa chỉ cổng 3000 của React) kèm theo tham số token
        String targetUrl = "http://localhost:5173/oauth2/redirect?token=" + token;

        // 4. Thực hiện lệnh chuyển hướng trình duyệt
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
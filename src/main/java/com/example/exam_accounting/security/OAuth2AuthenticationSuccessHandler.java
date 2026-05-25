package com.example.exam_accounting.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    // Tạm thời comment code JWT lại, chúng ta sẽ inject service sinh JWT vào đây ở bước sau
    // private final JwtTokenProvider tokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        // 1. Lấy thông tin đối tượng user đăng nhập thành công từ Spring Security
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        // 2. Tạm thời giả định một chuỗi token dummy để test luồng redirect sang FE trước khi viết code sinh mã JWT thật
        String fakeJwtToken = "dummy-token-for-testing-" + email;

        // 3. Xây dựng URL chuyển hướng về lại Frontend (địa chỉ cổng 3000 của React) kèm theo tham số token
        String targetUrl = UriComponentsBuilder.fromUriString("http://localhost:3000/oauth2/redirect")
                .queryParam("token", fakeJwtToken)
                .build().toUriString();

        // 4. Thực hiện lệnh chuyển hướng trình duyệt
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
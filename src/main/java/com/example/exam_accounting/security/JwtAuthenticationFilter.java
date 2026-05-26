package com.example.exam_accounting.security;

import com.example.exam_accounting.repository.IUserRepository; // Import Repository của bạn
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider tokenProvider;
    private final IUserRepository userRepository; // 1. Thay đổi từ UserDetailsService thành UserRepository trực tiếp

    // 2. Cập nhật lại Constructor tương ứng
    public JwtAuthenticationFilter(JwtTokenProvider tokenProvider, IUserRepository userRepository) {
        this.tokenProvider = tokenProvider;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);
//            System.out.println(">>> Token tu Postman: " + jwt);

            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                String username = tokenProvider.getUsernameFromJWT(jwt); // Đây chính là Email từ Google gửi về
//                System.out.println(">>> Email trong Token: " + username);
                // 3. Tìm thẳng User trong DB bằng Email (Thay cho hàm loadUserByUsername)
                // Giả định Entity User của bạn đã implements UserDetails.
                // Nếu chưa, hãy dùng: UserDetails userDetails = (UserDetails) userRepository.findByEmail(username).orElse(null);
                userRepository.findByEmail(username).ifPresent(user -> {
//                    System.out.println(">>> Da tim thay User trong DB: " + user.getEmail());
                    org.springframework.security.core.userdetails.UserDetails userDetails =
                            org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
                                    .password("") // Đăng nhập Google không cần password, để rỗng
                                    .authorities(user.getRole()) // Tạm thời để ROLE_USER để test, hoặc thay bằng quyền từ DB của bạn nếu có
                                    .build();

                    // Nạp vào hệ thống Spring Security
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                });
            }
        } catch (Exception ex) {
            logger.error("Không thể xác thực người dùng qua JWT", ex);
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
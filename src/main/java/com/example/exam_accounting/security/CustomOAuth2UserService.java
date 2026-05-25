package com.example.exam_accounting.security;

import com.example.exam_accounting.entity.User;
import com.example.exam_accounting.repository.IUserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final IUserRepository userRepository;

    public CustomOAuth2UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 1. Lấy thông tin sơ bộ của User từ Google (Email, Name, ID...)
        OAuth2User oAuth2User = super.loadUser(userRequest);

        try {
            return processOAuth2User(oAuth2User);
        } catch (Exception ex) {
            throw new OAuth2AuthenticationException(ex.getMessage());
        }
    }

    private OAuth2User processOAuth2User(OAuth2User oAuth2User) {
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email = (String) attributes.get("email");
        String fullName = (String) attributes.get("name");
        String googleId = (String) attributes.get("sub"); // 'sub' là ID định danh duy nhất của Google cấp

        if (email == null) {
            throw new IllegalArgumentException("Không tìm thấy thông tin Email từ nhà cung cấp Google");
        }

        // 2. Kiểm tra xem user này đã tồn tại trong DB chưa
        Optional<User> userOptional = userRepository.findByEmail(email);
        User user;

        if (userOptional.isPresent()) {
            user = userOptional.get();
            // Nếu user đã tồn tại nhưng chưa được liên kết googleId (ví dụ trước đó đăng ký bằng mật khẩu thường)
            if (user.getGoogleId() == null) {
                user.setGoogleId(googleId);
                userRepository.save(user);
            }
        } else {
            // 3. Nếu chưa tồn tại, tiến hành đăng ký tự động (INSERT) vào DB
            user = new User();
            user.setEmail(email);
            user.setFullName(fullName != null ? fullName : "Google User");
            user.setGoogleId(googleId);
            user.setRole("USER");     // Gán role mặc định theo cấu trúc varchar/enum của bạn
            user.setStatus("ACTIVE"); // Gán trạng thái mặc định
            // passwordHash để mặc định là null vì đăng nhập qua Google

            userRepository.save(user);
        }

        // 4. Trả về đối tượng OAuth2User hợp lệ cho Spring Security quản lý tiếp luồng
        return new DefaultOAuth2User(
                Collections.emptyList(), // Tạm thời để trống danh sách Grant Authorities (sẽ cấu hình phân quyền sau)
                attributes,
                "email" // Dùng thuộc tính 'email' làm key định danh chính
        );
    }
}
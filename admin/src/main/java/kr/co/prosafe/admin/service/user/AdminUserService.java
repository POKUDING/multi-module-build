package kr.co.prosafe.admin.service.user;

import org.springframework.stereotype.Service;

import kr.co.prosafe.core.domain.user.User;
import kr.co.prosafe.core.mapper.user.UserMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminUserService {

    private final UserMapper userMapper;

    /**
     * 사용자 정보를 조회합니다 (관리자용)
     * 
     * @return 사용자 정보
     */
    public User getUser() {
        log.info("Admin: Getting user information from database");
        
        try {
            User user = userMapper.selectUser();
            
            if (user != null) {
                log.info("Admin: Successfully retrieved user: {}", user.getName());
            } else {
                log.warn("Admin: No user found in database");
            }
            
            return user;
            
        } catch (Exception e) {
            log.error("Admin: Error retrieving user from database: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to retrieve user information", e);
        }
    }

    /**
     * 사용자 정보를 관리자 관점에서 처리합니다
     * 
     * @return 처리된 사용자 정보
     */
    public User getUserForAdmin() {
        log.info("Admin: Processing user information for admin panel");
        
        User user = getUser();
        
        if (user != null) {
            // 관리자 전용 로직 (예: 추가 정보 로딩, 권한 체크 등)
            log.info("Admin: Processing additional admin logic for user: {}", user.getName());
            
            // 실제로는 여기서 관리자 전용 비즈니스 로직 수행
            // 예: 사용자 통계, 권한 정보, 로그 히스토리 등
        }
        
        return user;
    }
}

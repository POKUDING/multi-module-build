package kr.co.prosafe.admin.controller.user;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.prosafe.admin.dto.common.AdminResponseDTO;
import kr.co.prosafe.admin.dto.user.response.AdminUserResponse;
import kr.co.prosafe.admin.service.user.AdminUserService;
import kr.co.prosafe.core.domain.user.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/admin/v1")
@RequiredArgsConstructor
public class AdminUserController {

    private final AdminUserService adminUserService;

    /**
     * 관리자용 사용자 정보 조회
     * 
     * @return 사용자 정보 (관리자 전용)
     */
    @GetMapping("/user")
    public ResponseEntity<AdminResponseDTO<AdminUserResponse>> getUser() {
        
        log.info("Admin API: Getting user information");
        
        try {
            User user = adminUserService.getUserForAdmin();
            
            if (user == null) {
                AdminResponseDTO<AdminUserResponse> response = AdminResponseDTO.<AdminUserResponse>builder()
                        .statusCode(HttpStatus.NOT_FOUND.value())
                        .message("User not found")
                        .adminInfo("No user data available in the system")
                        .build();
                
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            
            // 관리자용 응답 데이터 생성
            AdminUserResponse userResponse = AdminUserResponse.builder()
                    .name(user.getName())
                    .adminNotes("Retrieved from admin panel")
                    .retrievedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                    .build();
            
            AdminResponseDTO<AdminUserResponse> response = AdminResponseDTO.<AdminUserResponse>builder()
                    .statusCode(HttpStatus.OK.value())
                    .message("SUCCESS")
                    .data(userResponse)
                    .adminInfo("Data retrieved successfully by admin")
                    .build();
            
            log.info("Admin API: Successfully returned user information");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("Admin API: Error retrieving user information: {}", e.getMessage(), e);
            
            AdminResponseDTO<AdminUserResponse> response = AdminResponseDTO.<AdminUserResponse>builder()
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Internal server error")
                    .adminInfo("Error occurred while retrieving user data: " + e.getMessage())
                    .build();
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 관리자용 시스템 상태 체크
     * 
     * @return 시스템 상태
     */
    @GetMapping("/health")
    public ResponseEntity<AdminResponseDTO<String>> healthCheck() {
        
        log.info("Admin API: Health check requested");
        
        AdminResponseDTO<String> response = AdminResponseDTO.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Admin system is healthy")
                .data("OK")
                .adminInfo("All admin services are operational")
                .build();
        
        return ResponseEntity.ok(response);
    }
}

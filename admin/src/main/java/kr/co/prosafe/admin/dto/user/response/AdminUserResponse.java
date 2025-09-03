package kr.co.prosafe.admin.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class AdminUserResponse {

    /**
     * 사용자 이름
     */
    private String name;

    /**
     * 관리자 전용 정보
     */
    private String adminNotes;

    /**
     * 조회 시간
     */
    private String retrievedAt;
}

package kr.co.prosafe.admin.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminResponseDTO<T> {

    private int statusCode;

    private String message;

    private T data;

    /**
     * 관리자 전용 추가 정보
     */
    private String adminInfo;
}

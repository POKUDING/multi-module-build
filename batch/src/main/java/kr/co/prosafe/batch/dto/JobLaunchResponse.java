package kr.co.prosafe.batch.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobLaunchResponse {
    
    /**
     * 실행된 Job 이름
     */
    private String jobName;
    
    /**
     * Job 실행 ID
     */
    private Long executionId;
    
    /**
     * Job 실행 상태
     */
    private String status;
    
    /**
     * Job 시작 시간
     */
    private LocalDateTime startTime;
    
    /**
     * 응답 메시지
     */
    private String message;
}

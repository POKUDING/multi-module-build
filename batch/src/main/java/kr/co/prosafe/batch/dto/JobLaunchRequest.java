package kr.co.prosafe.batch.dto;

import java.util.Map;

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
public class JobLaunchRequest {
    
    /**
     * Job 실행 시 전달할 파라미터들
     */
    private Map<String, Object> parameters;
    
    /**
     * Job 실행 설명 (선택사항)
     */
    private String description;
}

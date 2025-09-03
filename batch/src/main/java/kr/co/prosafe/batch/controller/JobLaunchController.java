package kr.co.prosafe.batch.controller;

import java.util.Map;
import java.util.HashMap;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import kr.co.prosafe.batch.dto.JobLaunchRequest;
import kr.co.prosafe.batch.dto.JobLaunchResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/batch")
@RequiredArgsConstructor
public class JobLaunchController {

    private final JobLauncher jobLauncher;
    private final ApplicationContext applicationContext;

    /**
     * Job을 실행합니다
     * 
     * @param request Job 실행 요청 정보
     * @return Job 실행 결과
     */
    @PostMapping("/jobs/{jobName}/launch")
    public ResponseEntity<JobLaunchResponse> launchJob(
            @PathVariable String jobName,
            @RequestBody JobLaunchRequest request) {
        
        try {
            // Job Bean 조회
            Job job = applicationContext.getBean(jobName, Job.class);
            
            // Job Parameters 구성
            JobParametersBuilder parametersBuilder = new JobParametersBuilder();
            
            // 현재 시간을 파라미터로 추가 (Job 재실행을 위해)
            parametersBuilder.addLong("timestamp", System.currentTimeMillis());
            
            // 요청에서 받은 파라미터들 추가
            if (request.getParameters() != null) {
                request.getParameters().forEach((key, value) -> {
                    if (value instanceof String) {
                        parametersBuilder.addString(key, (String) value);
                    } else if (value instanceof Long) {
                        parametersBuilder.addLong(key, (Long) value);
                    } else if (value instanceof Double) {
                        parametersBuilder.addDouble(key, (Double) value);
                    } else {
                        parametersBuilder.addString(key, value.toString());
                    }
                });
            }
            
            JobParameters jobParameters = parametersBuilder.toJobParameters();
            
            // Job 실행
            JobExecution jobExecution = jobLauncher.run(job, jobParameters);
            
            log.info("Job [{}] started with execution id: {}", jobName, jobExecution.getId());
            
            // 응답 생성
            JobLaunchResponse response = JobLaunchResponse.builder()
                    .jobName(jobName)
                    .executionId(jobExecution.getId())
                    .status(jobExecution.getStatus().toString())
                    .startTime(jobExecution.getStartTime())
                    .message("Job launched successfully")
                    .build();
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("Failed to launch job [{}]: {}", jobName, e.getMessage(), e);
            
            JobLaunchResponse response = JobLaunchResponse.builder()
                    .jobName(jobName)
                    .message("Failed to launch job: " + e.getMessage())
                    .build();
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 실행 중인 Job의 상태를 조회합니다
     * 
     * @param executionId Job 실행 ID
     * @return Job 실행 상태
     */
    @GetMapping("/executions/{executionId}")
    public ResponseEntity<Map<String, Object>> getJobExecution(@PathVariable Long executionId) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            // JobExecution 조회 로직은 JobRepository를 통해 구현 가능
            // 현재는 간단한 응답만 반환
            result.put("executionId", executionId);
            result.put("message", "Job execution status endpoint");
            
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("Failed to get job execution [{}]: {}", executionId, e.getMessage(), e);
            result.put("error", "Failed to get job execution: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    /**
     * 등록된 모든 Job 목록을 조회합니다
     * 
     * @return Job 목록
     */
    @GetMapping("/jobs")
    public ResponseEntity<Map<String, Object>> getJobList() {
        Map<String, Object> result = new HashMap<>();
        
        try {
            // ApplicationContext에서 Job Bean들을 조회
            Map<String, Job> jobBeans = applicationContext.getBeansOfType(Job.class);
            
            result.put("jobs", jobBeans.keySet());
            result.put("count", jobBeans.size());
            
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("Failed to get job list: {}", e.getMessage(), e);
            result.put("error", "Failed to get job list: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }
}

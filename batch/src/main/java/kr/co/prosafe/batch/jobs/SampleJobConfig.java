package kr.co.prosafe.batch.jobs;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import kr.co.prosafe.core.domain.user.User;
import kr.co.prosafe.core.mapper.user.UserMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SampleJobConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final UserMapper userMapper;

    /**
     * 샘플 Job - 단순한 로그 출력 작업
     */
    @Bean
    public Job sampleJob() {
        return new JobBuilder("sampleJob", jobRepository)
                .start(sampleStep())
                .build();
    }

    /**
     * 샘플 Step
     */
    @Bean
    public Step sampleStep() {
        return new StepBuilder("sampleStep", jobRepository)
                .tasklet(sampleTasklet(), transactionManager)
                .build();
    }

    /**
     * 샘플 Tasklet - 실제 작업 로직
     */
    @Bean
    public Tasklet sampleTasklet() {
        return (contribution, chunkContext) -> {
            log.info("=== Sample Job Started ===");
            
            // Job Parameters 가져오기
            String timestamp = chunkContext.getStepContext()
                    .getJobParameters().get("timestamp").toString();
            log.info("Job executed with timestamp: {}", timestamp);
            
            // UserMapper를 통해 사용자 데이터 조회
            try {
                User user = userMapper.selectUser();
                if (user != null) {
                    log.info("Retrieved user from database: {}", user.getName());
                } else {
                    log.warn("No user data found in database");
                }
            } catch (Exception e) {
                log.error("Error retrieving user data: {}", e.getMessage(), e);
            }
            
            // 실제 비즈니스 로직 (예: 데이터 처리, 파일 생성 등)
            simulateWork();
            
            log.info("=== Sample Job Completed ===");
            return RepeatStatus.FINISHED;
        };
    }

    /**
     * 작업 시뮬레이션
     */
    private void simulateWork() {
        try {
            log.info("Processing data...");
            Thread.sleep(2000); // 2초 대기 (실제 작업 시뮬레이션)
            log.info("Data processing completed");
        } catch (InterruptedException e) {
            log.error("Job was interrupted", e);
            Thread.currentThread().interrupt();
        }
    }

    /**
     * 사용자 데이터 처리 Job 예시
     */
    @Bean
    public Job userDataProcessJob() {
        return new JobBuilder("userDataProcessJob", jobRepository)
                .start(userDataStep())
                .build();
    }

    @Bean
    public Step userDataStep() {
        return new StepBuilder("userDataStep", jobRepository)
                .tasklet(userDataTasklet(), transactionManager)
                .build();
    }

    @Bean
    public Tasklet userDataTasklet() {
        return (contribution, chunkContext) -> {
            log.info("=== User Data Processing Job Started ===");
            
            // 사용자 데이터 처리 로직
            log.info("Processing user data...");
            
            try {
                User user = userMapper.selectUser();
                if (user != null) {
                    log.info("Processing user: {}", user.getName());
                    // 실제 사용자 데이터 처리 로직
                    processUser(user);
                } else {
                    log.warn("No user data to process");
                }
            } catch (Exception e) {
                log.error("Error processing user data: {}", e.getMessage(), e);
            }
            
            Thread.sleep(1000);
            log.info("User data processing completed");
            
            log.info("=== User Data Processing Job Completed ===");
            return RepeatStatus.FINISHED;
        };
    }
    
    /**
     * 사용자 데이터 처리 로직
     */
    private void processUser(User user) {
        log.info("Processing user: {} - Starting business logic", user.getName());
        // 실제 비즈니스 로직 구현
        // 예: 데이터 변환, 외부 API 호출, 파일 생성 등
        log.info("Processing user: {} - Business logic completed", user.getName());
    }
}

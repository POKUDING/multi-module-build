package kr.co.prosafe.core;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import kr.co.prosafe.core.config.MybatisConfig;

@SpringJUnitConfig(classes = {MybatisConfig.class})
class CoreApplicationTests {

	@Test
	void contextLoads() {
		// 설정 클래스가 정상적으로 로드되는지 테스트
	}

}

package kr.co.prosafe.api.controller.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.prosafe.api.dto.common.ResponseDTO;
import kr.co.prosafe.api.dto.user.response.UserResponse;
import kr.co.prosafe.api.service.user.UserService;
import kr.co.prosafe.core.domain.user.User;

@RestController
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/v1/user")
	public ResponseEntity<ResponseDTO<UserResponse>> getUser() {

		User user = userService.getUser();

		return ResponseEntity.ok(
			ResponseDTO.<UserResponse>builder()
				.statusCode(HttpStatus.OK.value())
				.message("SUCCESS")
//				.data()
				.data(new UserResponse(user.getName()))
				.build()
		);
	}
}

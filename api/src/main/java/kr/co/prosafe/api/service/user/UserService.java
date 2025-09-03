package kr.co.prosafe.api.service.user;

import org.springframework.stereotype.Service;

import kr.co.prosafe.core.domain.user.User;
import kr.co.prosafe.core.mapper.user.UserMapper;

@Service
public class UserService {

	private final UserMapper userMapper;

	public UserService(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	public User getUser() {
		return userMapper.selectUser();
	}
}

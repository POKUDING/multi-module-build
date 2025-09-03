package kr.co.prosafe.core.mapper.user;


import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.prosafe.core.domain.user.User;

@Mapper
@Repository
public interface UserMapper {

	User selectUser();
}

package kr.co.prosafe.core.domain.user;

import org.apache.ibatis.type.Alias;

import lombok.Getter;

@Alias("user")
@Getter
public class User {
	private String name;
}

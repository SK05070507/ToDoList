package katachi.spring.exercise.application;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import katachi.spring.exercise.domain.user.model.MUser;
import katachi.spring.exercise.domain.user.service.UserService;

@Service
public class UserApplicationService {
	@Autowired
	UserService userService;
	// 担当者名のMapを作成
	public Map<String, Integer> getUserMap() {
		Map<String, Integer> userMap = new LinkedHashMap<>();
		List<MUser> users = userService.getUsers();
		for (MUser user : users) {
			String name = user.getFamilyName() + user.getFirstName();
			userMap.put(name, user.getUserId());
		}
		return userMap;
	}
}

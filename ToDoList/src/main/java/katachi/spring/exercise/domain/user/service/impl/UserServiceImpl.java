package katachi.spring.exercise.domain.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import katachi.spring.exercise.domain.user.model.MTask;
import katachi.spring.exercise.domain.user.model.MUser;
import katachi.spring.exercise.domain.user.service.UserService;
import katachi.spring.exercise.repository.UserMapper;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserMapper mapper;
	@Override
	//作業登録
	public void inputTask(MTask task) {
		mapper.insertOne(task);
	}
	@Override
	//作業一覧取得
	public List<MTask> getTasks(String taskName) {
		return mapper.findMany(taskName);
	}
	@Override
	//担当者一覧取得
	public List<MUser> getUsers() {
		return mapper.findUser();
	}
	@Override
	//作業更新
	public void updateTaskOne(MTask task) {
		mapper.updateOne(task);
	}
	@Override
	//対象の作業を取得
	public MTask getTaskOne(String id) {
		return mapper.findTask(id);
	}
	@Override
	//作業削除
	public void deleteOne(String id) {
		mapper.deleteOne(id);
	}
	@Override
	//作業完了
	public void finshedTask(Model model) {
		mapper.finshedTask(model);
	}

	@Override
	//作業完了取り消し
	public void removedTask(String id) {
		mapper.removedTask(id);
	}

}

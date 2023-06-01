package katachi.spring.exercise.domain.user.service;

import java.util.List;

import org.springframework.ui.Model;

import katachi.spring.exercise.domain.user.model.MTask;
import katachi.spring.exercise.domain.user.model.MUser;

public interface UserService {
	//作業登録
	public void inputTask(MTask task);
	//作業一覧取得
	public List<MTask> getTasks(String taskName);
	//担当者一覧取得
	public List<MUser> getUsers();
	//作業更新
	public void updateTaskOne(MTask task);
	//対象作業取得
	public MTask getTaskOne(String id);
	//作業削除
	public void deleteOne(String id);
	//作業完了
	public void finshedTask(Model model);
	//作業完了取り消し
	public void removedTask(String id);
}

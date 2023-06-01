package katachi.spring.exercise.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;

import katachi.spring.exercise.domain.user.model.MTask;
import katachi.spring.exercise.domain.user.model.MUser;

@Mapper
public interface UserMapper {

	//業務登録
	public int insertOne(MTask task);

	//業務一覧取得
	public List<MTask> findMany(@Param("name") String taskName);

	//ユーザー名取得
	public List<MUser> findUser();

	//業務更新
	public void updateOne(@Param("task") MTask task);

	//削除業務取得
	public MTask findTask(String id);

	//削除処理
	public void deleteOne(String id);

	//完了処理
	public void finshedTask(Model model);

	//未完了処理
	public void removedTask(String id);

	//ログインユーザー取得
	public UserDetails findLoginUser(String username);

}

package katachi.spring.exercise.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import katachi.spring.exercise.application.UserApplicationService;
import katachi.spring.exercise.domain.user.model.MTask;
import katachi.spring.exercise.domain.user.model.MUser;
import katachi.spring.exercise.domain.user.service.UserService;
import katachi.spring.exercise.form.InputForm;
import katachi.spring.exercise.form.updateForm;
/**
 * 作業管理画面のControllerクラス
 * @author K.Shirakawa
 *
 */
@Controller
public class TaskListController {

	@Autowired
	private UserService userService;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserApplicationService userApplicationService;

	/**
	 * 作業一覧画面へ遷移
	 * @param taskName 作業名
	 * @param model
	 * @return 作業一覧
	 */
	@GetMapping(value = "/task")
	public String getTask(String taskName, Model model) {
		//作業リスト取得
		List<MTask> taskList = userService.getTasks(taskName);
		model.addAttribute("taskList", taskList);	//作業リスト
		return "/task/list";
	}
	
	/**
	 * 作業登録画面へ遷移
	 * @param model
	 * @param form 作業登録フォーム
	 * @param user 作業担当者
	 * @return 作業登録画面
	 */
	@GetMapping("/task/input")
	public String getInput(Model model, 
			@ModelAttribute InputForm form,
			@ModelAttribute MUser user) {
		//担当名のMapを作成する	
		Map<String, Integer> userMap = userApplicationService.getUserMap();
		model.addAttribute("userMap", userMap);		//担当者名の選択肢
		return "/task/input";
	}

	/**
	 * 作業登録処理
	 * @param model
	 * @param form　作業登録フォーム
	 * @param bindingResult
	 * @param user　作業担当者
	 * @return　作業一覧
	 */
	@PostMapping("/task/input")
	public String postInput(Model model,
			@ModelAttribute @Validated InputForm form,
			BindingResult bindingResult,
			@ModelAttribute MUser user) {
		//フォームから作業情報を取得
		MTask task = modelMapper.map(form, MTask.class);
		//入力チェック
		if (bindingResult.hasErrors()) {
			return getInput(model, form, user);
		}
		//登録処理
		//createDatetimeとupdateDateTimeに現在日時を登録
		int del = 0;
		task.setRegistrationDate(new Date());
		task.setCreateDateTime(new Date());
		task.setUpdateDateTime(new Date());
		//作業完了フラグチェック
		task.setIsDeleted(del);
		if (form.getCheck() == null) {
			form.setCheck(0);
		}
		//作業完了の場合現在日時を取得
		if (form.getCheck() == 1) {
			task.setFinshedDate(new Date());
		}
		//登録処理
		userService.inputTask(task);
		return "redirect:/task/list";
	}

	/**
	 * 作業更新画面へ遷移
	 * @param id 作業ID
	 * @param model
	 * @param form 作業更新フォーム
	 * @return　作業更新
	 */
	@GetMapping("/task/update/{id}")
	public String getUpdateTask(
			@PathVariable("id") String id,
			Model model, 
			@ModelAttribute updateForm form) {
		//担当名のMapを作成する	
		Map<String, Integer> userMap = userApplicationService.getUserMap();
		//対象の作業を取得
		MTask task = userService.getTaskOne(id);
		model.addAttribute("task", task);			//更新対象作業
		model.addAttribute("userMap", userMap);		//担当者の選択肢
		model.addAttribute("taskId", id);			//作業ID
		return "/task/update";
	}

	/**
	 * 作業更新
	 * @param model
	 * @param form 作業更新フォーム
	 * @param bindingResult
	 * @return　作業一覧
	 */
	@PostMapping("/task/update/{id}")
	public String postUpdateTask(Model model,
			@ModelAttribute @Validated updateForm form,
			BindingResult bindingResult) {
		//フォームから更新したい作業IDを取得
		String id = form.getId();
		//フォームから更新したい作業情報を取得
		MTask task = modelMapper.map(form, MTask.class);
		//入力チェック
		if (bindingResult.hasErrors()) {
			return getUpdateTask(id, model, form);
		}
		//登録処理
		//createDatetimeとupdateDateTimeに現在日時を登録
		int del = 0;
		task.setRegistrationDate(new Date());
		task.setUpdateDateTime(new Date());
		//作業完了フラグチェック
		task.setIsDeleted(del);
		if (form.getCheck() == null) {
			form.setCheck(0);
		}
		//作業完了の場合現在日時を取得
		if (form.getCheck() == 1) {
			task.setFinshedDate(new Date());
		}
		//作業更新処理
		userService.updateTaskOne(task);
		return "redirect:/task/list";

	}

	/**
	 * 削除画面遷移
	 * @param id 作業ID
	 * @param model
	 * @return 削除画面
	 */
	@GetMapping("/task/deleted/{id}")
	public String getDeletedTask(@PathVariable("id") String id, Model model) {
		//対象の作業を取得
		MTask task = userService.getTaskOne(id);
		model.addAttribute("task", task);	//削除対象作業
		return "/task/deleted";
	}

	/**
	 * 削除処理
	 * @param id 作業ID
	 * @return　作業一覧
	 */
	@GetMapping("/task/deleted/try/{id}")
	public String deletedTask(@PathVariable("id") String id) {
		//削除処理
		userService.deleteOne(id);
		return "redirect:/task/list";
	}

	/**
	 * 完了処理
	 * @param id 作業ID
	 * @param model
	 * @return　作業一覧
	 */
	@GetMapping("/task/finshed/{id}")
	public String finshedTask(@PathVariable("id") String id, Model model) {
		//現在日時を取得
		Date finshedDate = new Date();
		model.addAttribute("finshedDate", finshedDate);		//完了日時
		model.addAttribute("taskId", id);					//作業ID
		//完了日時を登録
		userService.finshedTask(model);
		return "redirect:/task/list";
	}

	/**
	 * 未完了処理
	 * @param id 作業ID
	 * @return　作業一覧
	 */
	@GetMapping("/task/remove/{id}")
	public String removedTask(@PathVariable("id") String id) {
		//完了日時のリセット
		userService.removedTask(id);
		return "redirect:/task/list";
	}

	/**
	 * 検索処理と画面遷移
	 * @param taskName 作業名
	 * @param model
	 * @return　作業一覧
	 */
	@GetMapping(value = "/task/list")
	public String searchTask(@RequestParam(required = false) String taskName, Model model) {
		//作業リスト取得
		List<MTask> taskList = userService.getTasks(taskName);
		model.addAttribute("searchword", taskName);		//検索キーワード
		model.addAttribute("taskList", taskList);		//検索結果の作業リスト
		return "/task/list";
	}

}

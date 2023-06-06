package katachi.spring.exercise.domain.user.model;

import java.util.Date;

import lombok.Data;

@Data
public class MTask {
	//作業ID
	private String id; 
	//担当者ID
	private String userId;
	//作業名
	private String taskName;
	//登録日
	private Date registrationDate;
	//期限日
	private Date expireDate;
	//完了日
	private Date finshedDate;
	//削除フラグ
	private Integer isDeleted;
	//DB登録日時
	private Date createDateTime;
	//DB更新日時
	private Date updateDateTime;
	//担当者
	private MUser user;
	
	//HTML側でのアラート表示判別
	public boolean isAlert() {
		Date date = new Date();
		if (date.after(expireDate) && finshedDate == null) {
			return true;
		}
		return false;
	}
}

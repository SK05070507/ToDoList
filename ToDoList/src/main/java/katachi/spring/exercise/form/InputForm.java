package katachi.spring.exercise.form;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class InputForm {
	@Length(min = 0, max = 100)
	//作業名
	private String taskName;
	//担当者名
	private String userName;
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@NotNull
	//期限日
	private Date expireDate;
	//完了フラグ
	private Integer check;
}

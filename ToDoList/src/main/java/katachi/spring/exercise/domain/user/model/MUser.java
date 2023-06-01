package katachi.spring.exercise.domain.user.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
public class MUser implements UserDetails {
	//ユーザーID
	private Integer id;
	//メールアドレス
	private Integer userId;
	//苗字
	private String familyName;
	//名前
	private String firstName;
	private static final long serialVersionUID = 1L;
	//ユーザー名
	private String user;
	//パスワード
	private String password;
	private Collection<GrantedAuthority> authorities;
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	@Override
	public String getPassword() {
		return password;
	}
	@Override
	public String getUsername() {
		return user;
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return true;
	}
	//フルネーム作成
	public String getFullName() {
		return familyName + firstName;
	}
}

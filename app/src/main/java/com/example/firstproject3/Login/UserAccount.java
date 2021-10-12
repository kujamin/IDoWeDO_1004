package com.example.firstproject3.Login;

import java.util.HashMap;
import java.util.Map;

/**
 * 사용자 계정 정보 모델 클래스
 */

public class UserAccount {
    private String idtoken;     // Firebase Uid (고유 토큰정보)
    private String emailid;     // 이메일 아이디
    private String username;    // 사용자 이름
    private String repassword;  // 비밀번호 확인
    private String password;    // 비밀번호
    private String nickname;    // 어플 내 이름

    public UserAccount() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRepassword() {
        return repassword;
    }

    public void setRepassword(String repassword) {
        this.repassword = repassword;
    }

    public String getIdtoken() {
        return idtoken;
    }

    public void setIdtoken(String idtoken) {
        this.idtoken = idtoken;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() { return nickname; }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


    public UserAccount(String idtoken, String emailid, String repassword, String username , String password, String nickname) {
        this.idtoken = idtoken;
        this.emailid = emailid;
        this.password = password;
        this.nickname = nickname;
        this.username = username;
        this.repassword = repassword;


    }

}

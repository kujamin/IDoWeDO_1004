package com.example.firstproject3.Login;

import java.util.HashMap;
import java.util.Map;

/**
 * 사용자 계정 정보 모델 클래스
 */

public class UserAccount {
    private String idtoken;     // Firebase Uid (고유 토큰정보)
    private String emailid;     // 이메일 아이디
    private String password;    // 비밀번호
    private String nickname;    // 어플 내 이름
    private String level;      // 캐릭터 레벨
    private String exp;        // 캐릭터 경험치
    private String coin;       // 캐릭터 재화(돈)
    private String heart;      // 캐릭터 체력


    public UserAccount() {
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getHeart() {
        return heart;
    }

    public void setHeart(String heart) {
        this.heart = heart;
    }

    public UserAccount(String idtoken, String emailid, String password, String nickname, String level, String exp, String coin, String heart) {
        this.idtoken = idtoken;
        this.emailid = emailid;
        this.password = password;
        this.nickname = nickname;
        this.level = level;
        this.exp = exp;
        this.coin = coin;
        this.heart = heart;

    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("idtoken", idtoken);
        result.put("emailid", emailid);
        result.put("password", password);
        result.put("nickname", nickname);
        result.put("level", level);
        result.put("exp", exp);
        result.put("coin", coin);
        result.put("heart", heart);
        return result;
    }
}

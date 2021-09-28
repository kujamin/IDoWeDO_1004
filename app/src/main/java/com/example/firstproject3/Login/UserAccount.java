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
    private int level;      // 캐릭터 레벨
    private int exp;        // 캐릭터 경험치
    private int coin;       // 캐릭터 재화(돈)
    private int heart;      // 캐릭터 체력
    private int maxexp;     // 캐릭터 최대 경험치

    public int getMaxexp() {
        return maxexp;
    }

    public void setMaxexp(int maxexp) {
        this.maxexp = maxexp;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public int getHeart() {
        return heart;
    }

    public void setHeart(int heart) {
        this.heart = heart;
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


    public UserAccount(String idtoken, String emailid, String repassword, String username , String password, String nickname, int level, int exp, int coin, int heart, int maxexp) {
        this.idtoken = idtoken;
        this.emailid = emailid;
        this.password = password;
        this.nickname = nickname;
        this.level = level;
        this.exp = exp;
        this.coin = coin;
        this.heart = heart;
        this.username = username;
        this.repassword = repassword;
        this.maxexp = maxexp;

    }

    /*public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("idtoken", idtoken);
        result.put("emailid", emailid);
        result.put("username", username);
        result.put("repassword", repassword);
        result.put("password", password);
        result.put("nickname", nickname);
        result.put("level", level);
        result.put("exp", exp);
        result.put("coin", coin);
        result.put("heart", heart);
        return result;
    }*/
}

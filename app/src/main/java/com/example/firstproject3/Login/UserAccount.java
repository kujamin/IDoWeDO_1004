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
    private int datecnt;     // 출석체크
    private int challengepoint; //챌린지 참여 횟수
    private int dotodo;     //해낸 업무숫자
    private int storepoint; //상점 구매갯수

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

    public void setNickname(String nickname) { this.nickname = nickname; }

    public int getdatecnt() { return datecnt; }

    public void setdatecnt(int datecnt) { this.datecnt = datecnt; }

    public int getChallengepoint() { return  challengepoint; }

    public void setchallengepoint(int challengepoint) {this.challengepoint = challengepoint;}

    public int getDotodo() { return  dotodo; }

    public void setDotodo(int dotodo) {this.dotodo = dotodo;}

    public int getStorepoint() { return  storepoint; }

    public void setStorepoint(int storepoint) {this.storepoint = storepoint;}


    public UserAccount(String idtoken, String emailid, String repassword, String username , String password, String nickname, int datecnt, int challengepoint, int dotodo, int storepoint) {
        this.idtoken = idtoken;
        this.emailid = emailid;
        this.password = password;
        this.nickname = nickname;
        this.username = username;
        this.repassword = repassword;
        this.datecnt = datecnt;
        this.challengepoint = challengepoint;
        this.dotodo = dotodo;
        this.storepoint = storepoint;


    }

}

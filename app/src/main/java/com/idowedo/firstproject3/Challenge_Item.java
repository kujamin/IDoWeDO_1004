package com.idowedo.firstproject3;
//챌린지 리스트의 각 항목의 아이템들을 저장하는 클래스
public class Challenge_Item {
    private String challenge_image;
    private String challenge_title;
    private String challenge_content;

    public Challenge_Item(String challenge_image, String challenge_title, String challenge_content){
        this.challenge_image = challenge_image;
        this.challenge_title = challenge_title;
        this.challenge_content = challenge_content;
    }

    public String getChallenge_image() {
        return challenge_image;
    }

    public void setChallenge_image(String challenge_image) {
        this.challenge_image = challenge_image;
    }

    public String getChallenge_title() {
        return challenge_title;
    }

    public void setChallenge_title(String challenge_title) {
        this.challenge_title = challenge_title;
    }

    public String getChallenge_content() {
        return challenge_content;
    }

    public void setChallenge_content(String challenge_content) {
        this.challenge_content = challenge_content;
    }
}

package com.idowedo.firstproject3;
//타이머 리스트의 각 항목들의 아이템을 저장하는 클래스
public class Timer_Item {
    private String timerName;
    private String timerGoal;
    private String timerRecord;
    private String timerImgRec, timerId;

    public Timer_Item() {

    }

    public Timer_Item(String timerName, String timerGoal, String timerRecord, String timerImgRec, String timerId) {
        this.timerName = timerName;
        this.timerGoal = timerGoal;
        this.timerRecord = timerRecord;
        this.timerImgRec = timerImgRec;
        this.timerId = timerId;
    }

    public String getTimerName() {
        return timerName;
    }

    public void setTimerName(String timerName) {
        this.timerName = timerName;
    }

    public String getTimerGoal() {
        return timerGoal;
    }

    public void setTimerGoal(String timerGoal) {
        this.timerGoal = timerGoal;
    }

    public String getTimerRecord() {
        return timerRecord;
    }

    public void setTimerRecord(String timerRecord) {
        this.timerRecord = timerRecord;
    }

    public String getTimerImgRec() {
        return timerImgRec;
    }

    public void setTimerImgRec(String timerImgRec) {
        this.timerImgRec = timerImgRec;
    }

    public String getTimerId() {
        return timerId;
    }

    public void setTimerId(String timerId) {
        this.timerId = timerId;
    }
}

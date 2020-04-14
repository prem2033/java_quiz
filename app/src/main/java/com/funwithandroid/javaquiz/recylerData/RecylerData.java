package com.funwithandroid.javaquiz.recylerData;

public class RecylerData {
    public   String type="type";
    public  String highscore="highscore";

    public RecylerData(String type, String highscore) {
        this.type = type;
        this.highscore = highscore;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHighscore() {
        return highscore;
    }

    public void setHighscore(String highscore) {
        this.highscore = highscore;
    }
}

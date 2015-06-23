package com.dutao.mymobileguard.bean;

import java.io.Serializable;

/**
 * Created by dutao on 2015/6/16.
 */
public class BlackNumberInfo implements Serializable {
    private String number;
    private String mode;

    public BlackNumberInfo(String number, String mode) {
        this.number = number;
        this.mode = mode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}

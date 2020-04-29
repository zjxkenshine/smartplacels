package com.csdl.smartplacenew.constant;

public enum PlaceEnum {
    SongYang("15","松阳"),
    QingYuan("16","庆元"),
    QingTian("3","青田"),
    JinYun("14","缙云");

    private String code;

    private String message;

    PlaceEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String getCode(String message) {
        if(ContainCode(message)){
            return message;
        }
        for (PlaceEnum c : PlaceEnum.values()) {
            if (c.getMessage().equals(message)) {
                return c.getCode();
            }
        }
        return null;
    }

    public static String getMessage(String code) {

        for (PlaceEnum c : PlaceEnum.values()) {
            if (c.getCode().equals(code)) {
                return c.getMessage();
            }
        }
        return null;
    }

    public static Boolean ContainCode(String code) {
        for (PlaceEnum c : PlaceEnum.values()) {
            if (c.getCode().equals(code)) {
                return true;
            }
        }
        return false;
    }

    public static Boolean ContainMessage(String message) {
        for (PlaceEnum c : PlaceEnum.values()) {
            if (c.getMessage().equals(message)) {
                return true;
            }
        }
        return false;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

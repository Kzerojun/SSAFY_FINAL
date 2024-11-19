package com.enjoytrip.util;

public class ParameterCheck {

    private static ParameterCheck instance = new ParameterCheck();

    private ParameterCheck() {
        // private 생성자로 외부에서 인스턴스 생성 방지
    }

    public static ParameterCheck getInstance() {
        return instance;
    }

    public String nullToBlank(String str) {
        return str == null ? "" : str;
    }

    public int notNumberToZero(String str) {
        if(isNumber(str))
            return Integer.parseInt(str);
        else
            return 0;
    }

    public int notNumberToOne(String str) {
        if(isNumber(str))
            return Integer.parseInt(str);
        else
            return 1;
    }

    private boolean isNumber(String str) {
        boolean isNum = true;
        if(str == null || str.length() == 0)
            isNum = false;
        else {
            for (int i = 0; i < str.length(); i++) {
                int num = str.charAt(i) - 48;
                if(num < 0 || num > 9) {
                    isNum = false;
                    break;
                }
            }
        }
        return isNum;
    }
}
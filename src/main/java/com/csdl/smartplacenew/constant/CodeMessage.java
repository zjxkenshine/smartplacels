package com.csdl.smartplacenew.constant;

public enum CodeMessage {
    RegistSuccess("200", "注册成功"),
    RegistError("400", "注册失败"),
    VerificateCodeError("400", "验证码发送错误"),
    VerificateCodeSuccess("200", "验证码发送成功"),
    UserExit("400", "用户存在"),
    UserNotExit("200", "用户不存在"),
    UserNotLogin("401", "用户未登入"),
    MobileNotExit("400", "手机号对应用户不存在"),
    MobileExit("400", "手机号对应用户存在"),
    EamilNotExit("400", "邮箱对应用户不存在"),
    UserNameNotExit("400", "用户名对应用户不存在"),
    UserNameExit("400", "用户名对应用户存在"),
    UserNotAdmin("400", "用户非管理员"),
    PassWordError("400", "密码错误"),
    TerrainNotExit("400", "地块不存在"),
    PlanNotExit("400", "方案不存在"),
    SourceFileFormatError("400", "文件格式不正确"),
    ModelNotExit("400", "模型不存在"),
    ModelConverting("200", "模型正在转换"),
    ModelConverted("200", "模型转换完成"),
    ModelConvertFailed("400", "模型转换失败"),
    UserLoginFailByLock("400", "您已被锁定");

    private String code;

    private String message;

    CodeMessage(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String getName(String code) {
        for (CodeMessage c : CodeMessage.values()) {
            if (c.getCode() == code) {
                return c.message;
            }
        }
        return null;
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

package com.yj.utils;

public enum AppHttpCodeEnum {
     SUCCESS(200,"操作成功"),
    NEED_LOGIN(401,"需要登录后操作"),
    NO_OPERATOR_AUTH(403,"无权限操作"),
    SYSTEM_ERROR(500,"出现错误"),
    USERNAME_EXIT(501,"用户名已存在"),NICKNAME_EXIT(510,"昵称已存在"),
    PHONE_NUMBER_EXIST(502,"手机号已存在"),EMAIL_EXIST(503,"邮箱已存在"),
    REQUIRE_USERNAME(504,"必须填写用户名"),
    LOGIN_ERROR(505,"用户名或密码错误"),
    USERNAME_NOT_NULL(506,"用户名不能为空"),
    NICKNAME_NOT_NULL(507,"昵称不能为空"),
    PASSWORD_NOT_NULL(508,"密码不能为空"),
    EMAIL_NOT_NULL(509,"邮箱不能为空"), CONTENT_NOT_NULL(511,"评论内容不能为空" ), FILE_TYPE_ERROR(511,"图片格式错误，请上传png和jpg文件" ), TAG_NAME_NOT_NULL(512,"标签名称不能为空" );

     Integer code;
     String msg;

     AppHttpCodeEnum(Integer code,String msg){
         this.code = code;
         this.msg = msg;
     }

     public Integer getCode(){
         return code;
     }
     public String getMsg(){
         return msg;
     }
}

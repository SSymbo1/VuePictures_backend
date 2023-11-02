package com.application.backend.entity;

import com.application.backend.utils.ResultCode;
import java.util.HashMap;
import java.util.Map;
public class Result {
    private Boolean success;
    private Integer code;
    private String message;
    private Map<String, Object> data=new HashMap<>();

    private Result(){}
    public static Result ok(){
        Result result=new Result();
        result.setSuccess(true);
        result.setCode(ResultCode.SUCCESS);
        result.setMessage("成功登录!");
        return result;
    }

    public static Result error(){
        Result result=new Result();
        result.setSuccess(false);
        result.setCode(ResultCode.ERROR);
        result.setMessage("登录失败!");
        return result;
    }

    public static Result register_ok(){
        Result result=new Result();
        result.setSuccess(true);
        result.setCode(ResultCode.REGISTER_SUCCESS);
        result.setMessage("注册成功!");
        return result;
    }

    public static Result register_error(){
        Result result=new Result();
        result.setSuccess(false);
        result.setCode(ResultCode.REGISTER_ERROR);
        result.setMessage("注册失败!");
        return result;
    }

    public static Result register_error_captcha(){
        Result result=new Result();
        result.setSuccess(false);
        result.setCode(ResultCode.REGISTER_ERROR_CAPTCHA);
        result.setMessage("验证码错误!");
        return result;
    }

    public static Result register_exist(){
        Result result=new Result();
        result.setSuccess(false);
        result.setCode(ResultCode.REGISTER_EXIST);
        result.setMessage("该用户已存在!");
        return result;
    }

    public static Result update_background_success(){
        Result result=new Result();
        result.setSuccess(true);
        result.setCode(ResultCode.UPDATE_BACKGROUND_SUCCESS);
        result.setMessage("更新背景图片成功!");
        return result;
    }

    public static Result update_background_error(){
        Result result=new Result();
        result.setSuccess(false);
        result.setCode(ResultCode.UPDATE_BACKGROUND_ERROR);
        result.setMessage("更新背景图片失败!");
        return result;
    }

    public static Result update_titlehead_success(){
        Result result=new Result();
        result.setSuccess(true);
        result.setCode(ResultCode.UPDATE_TITLE_SUCCESS);
        result.setMessage("更新用户头像成功!");
        return result;
    }

    public static Result update_titlehead_error(){
        Result result=new Result();
        result.setSuccess(false);
        result.setCode(ResultCode.UPDATE_TITLE_ERROR);
        result.setMessage("更新用户头像失败!");
        return result;
    }

    public static Result update_user_info_success(){
        Result result=new Result();
        result.setSuccess(true);
        result.setCode(ResultCode.UPDATE_USER_INFO_SUCCESS);
        result.setMessage("更新用户信息成功!");
        return result;
    }

    public static Result update_user_info_error(){
        Result result=new Result();
        result.setSuccess(false);
        result.setCode(ResultCode.UPDATE_USER_INFO_ERROR);
        result.setMessage("更新用户信息失败!");
        return result;
    }

    public static Result email_error(){
        Result result=new Result();
        result.setSuccess(false);
        result.setCode(ResultCode.EMAIL_ERROR);
        result.setMessage("验证码邮件发送失败，检查邮件地址是否正确！");
        return result;
    }
    public static Result email_success(){
        Result result=new Result();
        result.setSuccess(true);
        result.setCode(ResultCode.EMAIL_SUCCESS);
        result.setMessage("验证码邮件已发送！");
        return result;
    }
    public static Result history_update(){
        Result result=new Result();
        result.setSuccess(true);
        result.setCode(ResultCode.HISTORY_UPDATE);
        result.setMessage("历史记录更新成功！");
        return result;
    }
    public static Result history_delete(){
        Result result=new Result();
        result.setSuccess(true);
        result.setCode(ResultCode.HISTORY_DELETE);
        result.setMessage("历史记录删除成功！");
        return result;
    }
    public static Result history_add(){
        Result result=new Result();
        result.setSuccess(true);
        result.setCode(ResultCode.HISTORY_SUCCESS);
        result.setMessage("历史记录添加成功！");
        return result;
    }
    public static Result history_error(){
        Result result=new Result();
        result.setSuccess(true);
        result.setCode(ResultCode.HISTORY_ERROR);
        result.setMessage("历史记录操作异常");
        return result;
    }

    public Result success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public Result message(String message){
        this.setMessage(message);
        return this;
    }

    public Result code(Integer code){
        this.setCode(code);
        return this;
    }

    public Result data(String key,Object value){
        this.data.put(key,value);
        return this;
    }

    public Result data(Map<String,Object> map){
        this.setData(map);
        return this;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}

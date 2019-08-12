package com.job.core.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.job.core.exception.BizException;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;


/**
 * @author lzl
 * @createTime 2017-12-13 10:55
 */
public class Result<T> implements Serializable {
    public static final String DEF_ERROR_MESSAGE = "系统繁忙，请稍候再试";
    public static final String HYSTRIX_ERROR_MESSAGE = "请求超时，请稍候再试";
    private static final int SUCCESS = 1;
    private static final int FAIL = -1;
    private static final int LOGIN_FAIL = 401;

    /**
     * 调用是否成功标识，0：成功，-1:系统繁忙，此时请开发者稍候再试 详情见[ExceptionCode]
     */
    private int errcode;

    /**
     * 调用结果
     */
    private T data;

    /**
     * 结果消息，如果调用成功，消息通常为空T
     */
    private String errmsg = "ok";

    private Object errorEnum;


    private String optRemark;//日志方法
    private Long optUserId;//操作人id
    private String optIp;//操作人ip
    private String optUrl;//操作url


    private Result() {
        super();
    }

    public Result(int errcode, T data, String errmsg) {
        this.errcode = errcode;
        this.data = data;
        this.errmsg = errmsg;
    }

    public Result(int errcode, Object errorEnum) {
        this.errcode = errcode;
        this.errorEnum = errorEnum;
    }

    /**
     * 请求成功消息
     *
     * @param data 结果
     * @return RPC调用结果
     */
    public static <E> Result<E> success(E data) {
        return new Result<>(SUCCESS, data, "ok");
    }


    /**
     * 请求成功消息
     *
     * @param data 结果
     * @return RPC调用结果
     */
    public static <E> Result<E> fail(E data) {
        return new Result<>(FAIL, data, DEF_ERROR_MESSAGE);
    }

    /**
     * 请求成功方法 ，data返回值，msg提示信息
     *
     * @param data 结果
     * @param msg  消息
     * @return RPC调用结果
     */
    public static <E> Result<E> success(E data, String msg) {
        return new Result<>(SUCCESS, data, msg);
    }

    /**
     * 请求成功方法 ，data返回值，msg提示信息
     *
     * @return RPC调用结果
     **/
    public static <E> Result<E> success() {
        return new Result<E>(SUCCESS, "执行完成");
    }

    /**
     * 请求失败消息
     *
     * @param msg
     * @return
     */
    public static <E> Result<E> fail(int code, String msg) {

        return new Result<>(code, null, (msg == null || msg.isEmpty()) ? DEF_ERROR_MESSAGE : msg);
    }

    public static <E> Result<E> failEnum(Object data) {
        return new Result<>(FAIL, data);
    }

    /**
     * 如果错误枚举里的内容需要格式化，就使用这个
     *
     * @param data
     * @param format
     * @param <E>
     * @return
     */
    public static <E> Result<E> failEnumWithFormat(Object data, String[] format, String[] thaiFormat) {

        //通过反射修改data里的数据
        try {

            //反射拿到类的类型
            Class<?> dataClass = data.getClass();

            //获取异常编码
            Field codeField = dataClass.getDeclaredField("code");
            codeField.setAccessible(true); // 设置些属性是可以访问的
            String code = codeField.get(data).toString();

            //修改msg字段
            Field msgField = dataClass.getDeclaredField("msg");
            msgField.setAccessible(true); // 设置些属性是可以访问的
            String f = String.format(msgField.get(data).toString(), format);
//            msgField.set(data, f);

            //修改thaiMsg字段
            Field thaiMsgField = dataClass.getDeclaredField("thaiMsg");
            thaiMsgField.setAccessible(true); // 设置些属性是可以访问的
            String tf = String.format(thaiMsgField.get(data).toString(), thaiFormat);
//            thaiMsgField.set(data, tf);

            //生成新的返回值
            Map<String, String> map = new HashMap<>();
            map.put("code", code);
            map.put("msg", f);
            map.put("thaiMsg", tf);
            return new Result<>(FAIL, map);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return new Result<>(FAIL, data);
    }


    public static <E> Result<E> fail(String msg) {
        return fail(FAIL, msg);
    }


   /* public static <E> Result<E> fail(BaseExceptionCode exceptionCode) {
        return new Result<>(exceptionCode.getCode(), null,
                (exceptionCode.getMsg() == null || exceptionCode.getMsg().isEmpty()) ? DEF_ERROR_MESSAGE : exceptionCode.getMsg());
    }*/

    public static <E> Result<E> fail(BizException exception) {
        if (exception == null) {
            return fail(DEF_ERROR_MESSAGE);
        }
        return new Result<>(exception.getCode(), null, exception.getMessage());
    }

    public static <E> Result<E> loginFail(Object data) {
        return new Result<>(LOGIN_FAIL, data);
    }

    /**
     * 请求失败消息，根据异常类型，获取不同的提供消息
     *
     * @param throwable 异常
     * @return RPC调用结果
     */
    public static <E> Result<E> fail(Throwable throwable) {
        return fail(throwable != null ? throwable.getMessage() : DEF_ERROR_MESSAGE);
    }

    public static <E> Result<E> timeout() {
        return fail(HYSTRIX_ERROR_MESSAGE);
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getOptRemark() {
        return optRemark;
    }

    public void setOptRemark(String optRemark) {
        this.optRemark = optRemark;
    }

    public Long getOptUserId() {
        return optUserId;
    }

    public void setOptUserId(Long optUserId) {
        this.optUserId = optUserId;
    }

    public String getOptIp() {
        return optIp;
    }

    public void setOptIp(String optIp) {
        this.optIp = optIp;
    }

    public String getOptUrl() {
        return optUrl;
    }

    public void setOptUrl(String optUrl) {
        this.optUrl = optUrl;
    }

    public Object getErrorEnum() {
        return errorEnum;
    }

    public void setErrorEnum(Object errorEnum) {
        this.errorEnum = errorEnum;
    }

    /**
     * 逻辑处理是否成功
     *
     * @return 是否成功
     */
    @JsonIgnore
    public boolean isSuccess() {
        return this.errcode == SUCCESS;
    }
}

package com.zhijia.crowd.util;

/**统一整个项目中Ajax请求返回的结果
 * @author zhijia
 * @create 2022-03-14 23:15
 */
public class ResultEntity<T>  {

    public static final String SUCCESS="SUCCESS";
    public static final String FAILED="FAILED";

    //用来封装当前请求处理的结果是成功还是失败
    private String result;

    //请求处理失败时返回消息
    private String message;

    //要返回的数据
    private T data;

    //成功且不需要返回数据
    public static <E>ResultEntity<E> successWithoutData(){
        return new ResultEntity<E>(SUCCESS,null,null);
    }
    //成功且需要返回数据
    public static <E>ResultEntity<E> successWithData(E data){
        return new ResultEntity<E>(SUCCESS,null,data);
    }
    //失败并返回消息
    public static <E>ResultEntity<E> failed(String message){
        return new ResultEntity<E>(FAILED,message,null);
    }

    public ResultEntity() {
    }

    public ResultEntity(String result, String message, T data) {
        this.result = result;
        this.message = message;
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultEntity{" +
                "result='" + result + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

package com.zhijia.crowd.util;

import com.aliyun.api.gateway.demo.util.HttpUtils;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.comm.ResponseMessage;
import com.aliyun.oss.model.PutObjectResult;
import com.zhijia.crowd.constart.CrowdConstant;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 请求工具类
 *
 * @author zhijia
 * @create 2022-03-15 10:59
 */
public class CrowdUtil {

    /**
     * 专门负责上传文件到OSS 服务器的工具方法
     *
     * @param endpoint        OSS 参数
     * @param accessKeyId     OSS 参数
     * @param accessKeySecret OSS 参数
     * @param inputStream     要上传的文件的输入流
     * @param bucketName      OSS 参数
     * @param bucketDomain    OSS 参数
     * @param originalName    要上传的文件的原始文件名
     * @return 包含上传结果以及上传的文件在OSS 上的访问路径
     */
    public static ResultEntity<String> uploadFileToOss(
                                String endpoint,
                                String accessKeyId,
                                String accessKeySecret,
                                InputStream inputStream,
                                String bucketName,
                                String bucketDomain,
                                String originalName) {
        // 创建OSSClient 实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 生成上传文件的目录
        String folderName = new SimpleDateFormat("yyyyMMdd").format(new Date());
        // 生成上传文件在OSS 服务器上保存时的文件名
        // 原始文件名：beautfulgirl.jpg
        // 生成文件名：wer234234efwer235346457dfswet346235.jpg
        // 使用UUID 生成文件主体名称
        String fileMainName = UUID.randomUUID().toString().replace("-", "");
        // 从原始文件名中获取文件扩展名
        String extensionName = originalName.substring(originalName.lastIndexOf("."));
        // 使用目录、文件主体名称、文件扩展名称拼接得到对象名称
        String objectName = folderName + "/" + fileMainName + extensionName;
        try {
            // 调用OSS 客户端对象的方法上传文件并获取响应结果数据
            PutObjectResult putObjectResult = ossClient.putObject(bucketName, objectName,
                    inputStream);
            // 从响应结果中获取具体响应消息
            ResponseMessage responseMessage = putObjectResult.getResponse();
            // 根据响应状态码判断请求是否成功
            if (responseMessage == null) {
                // 拼接访问刚刚上传的文件的路径
                String ossFileAccessPath = bucketDomain + "/" + objectName;
                // 当前方法返回成功
                return ResultEntity.successWithData(ossFileAccessPath);
            } else {
                // 获取响应状态码
                int statusCode = responseMessage.getStatusCode();
                // 如果请求没有成功，获取错误消息
                String errorMessage = responseMessage.getErrorResponseAsString();
                // 当前方法返回失败
                return ResultEntity.failed(" 当前响应状态码=" + statusCode + " 错误消息" + errorMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 当前方法返回失败
            return ResultEntity.failed(e.getMessage());
        } finally {
            if (ossClient != null) {
                // 关闭OSSClient。
                ossClient.shutdown();
            }
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        FileInputStream inputStream = new FileInputStream("atcrowdfunding05-common-util/3.jpg");
        ResultEntity<String> resultEntity = uploadFileToOss("http://oss-cn-shenzhen.aliyuncs.com",
                "LTAI5tGr5a9V16dbYkBfS6wn",
                "2A8itxxSoRH37tFXMNRm5w69wzXIDz", inputStream, "zhijia1998",
                "http://zhijia1998.oss-cn-shenzhen.aliyuncs.com", "3.jpg");
        System.out.println(resultEntity);
    }


    public static ResultEntity<String> sendShortMessage(String iHost, String iPath, String iMethod,
                                                        String phoneNum, String appCode, String stgn) {
        String host = iHost;
        String path = iPath;
        String method = iMethod;
        String appcode = appCode;
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();

        //随机生成验证码
        StringBuilder builder = new StringBuilder(4);
        for (int i = 0; i < 4; i++) {
            int random = (int) (Math.random() * 10);
            builder.append(random);
        }

        bodys.put("content", "code:" + builder.toString());
        bodys.put("phone_number", phoneNum);
        bodys.put("template_id", stgn);


        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            String reasonPhrase = statusLine.getReasonPhrase();
            System.out.println(response.toString());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
            if (statusCode == 200) {
                return ResultEntity.successWithData(builder.toString());
            }
            return ResultEntity.failed(reasonPhrase);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }


    public static ResultEntity<String> sendShortMessage() {
        return sendShortMessage("https://gyytz.market.alicloudapi.com", "/sms/smsSend",
                "POST", "15307620821", "b81a499b1f46480fa077f146f4ea43c9",
                "2e65b1bb3d054466b82f0c9d125465e2", "908e94ccf08b4476ba6c876d13f084ad");
    }

    //发送验证码（接口调用URL地址/短信功能地址/请求方式/手机号/第三方的AppCode/签名/模板
    public static ResultEntity<String> sendShortMessage(String iHost, String iPath, String iMethod,
                                                        String phoneNum, String appCode, String stgn, String skin) {
        String host = iHost;
        String path = iPath;
        String method = iMethod;
        String appcode = appCode;
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();

        //随机生成验证码
        StringBuilder builder = new StringBuilder(4);
        for (int i = 0; i < 4; i++) {
            int random = (int) (Math.random() * 10);
            builder.append(random);
        }

        querys.put("param", "**code**:" + builder.toString() + ",**minute**:5");
        querys.put("mobile", phoneNum);

        querys.put("smsSignId", stgn);
        querys.put("templateId", skin);
        Map<String, String> bodys = new HashMap<String, String>();


        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            String reasonPhrase = statusLine.getReasonPhrase();
            System.out.println(response.toString());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
            if (statusCode == 200) {
                return ResultEntity.successWithData(builder.toString());
            }
            return ResultEntity.failed(reasonPhrase);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    //对字符串进行MD%加密
    public static String md5(String source) {
        //1、是否有效
        if (source == null || source.length() == 0) {
            //2、无效抛异常
            throw new RuntimeException(CrowdConstant.MESSAGE_STRING_INVALIDATE);
        }

        try {
            //3、获取MessageDigest对象
            String algorithm = "md5";

            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);

            //4、获取明文字字符串对应的字节数组
            byte[] input = source.getBytes();

            //5、执行加密
            byte[] output = messageDigest.digest(input);

            //6、创建BigInteger对象  1为正数
            BigInteger bigInteger = new BigInteger(1, output);
            //7、按照16进制将Integer转化字符串
            String encoded = bigInteger.toString(16).toUpperCase();
            return encoded;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }


    //判断当前请求是否为ajax请求
    public static boolean judgeRequestType(HttpServletRequest request) {

        //1、获取请求消息头
        String accept = request.getHeader("Accept");
        String xRequestHeader = request.getHeader("X-Requested-With");

        //2、判断
        return ((accept != null && accept.contains("application/json")) ||
                (xRequestHeader != null && xRequestHeader.equals("XMLHttpRequest")));
    }
}

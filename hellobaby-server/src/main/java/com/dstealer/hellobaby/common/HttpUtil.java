package com.dstealer.hellobaby.common;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.security.cert.X509Certificate;
import java.util.Map;

/**
 * Http 连接工具类
 * Created by LiShiwu on 15/9/2.
 */
public class HttpUtil {
    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtil.class);
    /**
     * 使用的默认字符集
     */
    private static final String CHARSET_UTF8 = "UTF-8";

    static {
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("SSL"); //或TSL
            X509TrustManager[] xtmArray = new X509TrustManager[]{new X509TrustManager() {

                public void checkClientTrusted(X509Certificate[] chain, String authType) {
                }

                public void checkServerTrusted(X509Certificate[] chain, String authType) {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            }};
            sslContext.init(null, xtmArray, new java.security.SecureRandom());
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        if (sslContext != null) {
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
        }
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
    }

    /**
     * 以Post方式发送http消息
     *
     * @param url
     * @param headers
     * @param params
     * @param timeOutInSeconds
     * @return
     * @throws Exception
     */
    public static String get(String url, Map<String, String> headers, Map<String, String> params, int timeOutInSeconds) {
        try {
            //处理get URL参数
            StringBuilder paramsBuilder = new StringBuilder();
            if (params != null && params.size() > 0) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    paramsBuilder.append(URLEncoder.encode(entry.getKey(), CHARSET_UTF8)).append("=")
                            .append(URLEncoder.encode(entry.getValue(), CHARSET_UTF8)).append("&");
                }
                paramsBuilder.deleteCharAt(paramsBuilder.length() - 1);
            }
            return get(url, headers, paramsBuilder.toString(), timeOutInSeconds);
        } catch (Exception e) {
            LOGGER.error("Error", e);
        }
        return null;
    }

    /**
     * 以Post方式发送http消息
     *
     * @param url
     * @param headers
     * @param params
     * @param timeOutInSeconds
     * @return
     * @throws Exception
     */

    public static String get(String url, Map<String, String> headers, String params, int timeOutInSeconds) {
        HttpURLConnection http = null;
        BufferedInputStream bis = null;
        try {
            //处理get URL参数
            if (params != null) {
                url = url + "?" + params;
            }
            URL urlObject = new URL(url);
            http = (HttpURLConnection) urlObject.openConnection();
            http.setRequestMethod("GET");
            http.setUseCaches(false);
            http.setConnectTimeout(timeOutInSeconds * 1000);
            http.setReadTimeout(timeOutInSeconds * 1000);
            //处理请求头
            if (headers != null && headers.size() > 0) {
                for (Map.Entry<String, String> header : headers.entrySet()) {
                    http.setRequestProperty(header.getKey(), header.getValue());
                }
            }
            http.connect();
            //正常返回否则 返回null
            if (http.getResponseCode() == 200) {
                bis = new BufferedInputStream(http.getInputStream());
                return URLDecoder.decode(new String(IOUtils.toByteArray(bis), CHARSET_UTF8), CHARSET_UTF8);
            } else {
                LOGGER.warn("Http response code:{}", http.getResponseCode());
            }
        } catch (Exception e) {
            LOGGER.error("Error", e);
        } finally {
            IOUtils.closeQuietly(bis);
            if (http != null) http.disconnect();
        }
        return null;
    }

    /**
     * 以Post方式发送http消息
     *
     * @param url
     * @param headers
     * @param params
     * @param timeOutInSeconds
     * @return
     * @throws Exception
     */
    public static String post(String url, Map<String, String> headers, Map<String, String> params, int timeOutInSeconds) {
        try {
            StringBuilder paramsBuilder = new StringBuilder();
            if (params != null && params.size() > 0) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    paramsBuilder.append(URLEncoder.encode(entry.getKey(), CHARSET_UTF8)).append("=")
                            .append(URLEncoder.encode(entry.getValue(), CHARSET_UTF8)).append("&");

                }
                paramsBuilder.deleteCharAt(paramsBuilder.length() - 1);
            }
            return post(url, headers, paramsBuilder.toString(), timeOutInSeconds);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("Error", e);
        }
        return null;
    }

    /**
     * 以Post方式发送http消息
     *
     * @param url
     * @param headers
     * @param body
     * @param timeOutInSeconds
     * @return
     * @throws Exception
     */
    public static String post(String url, Map<String, String> headers, String body, int timeOutInSeconds) {
        HttpURLConnection http = null;
        BufferedOutputStream bos = null;
        BufferedInputStream bis = null;
        try {
            URL urlObject = new URL(url);
            http = (HttpURLConnection) urlObject.openConnection();
            //是否有参数
            http.setDoOutput(body != null);
            http.setRequestMethod("POST");
            http.setUseCaches(false);
            http.setConnectTimeout(timeOutInSeconds * 1000);
            http.setReadTimeout(timeOutInSeconds * 1000);
            if (headers != null && headers.size() > 0) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    http.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            http.connect();
            if (body != null) {
                bos = new BufferedOutputStream(http.getOutputStream());
                bos.write(body.getBytes(CHARSET_UTF8));
                bos.flush();
                bos.close();
            }
            //正常返回 否则返回null
            if (http.getResponseCode() == 200) {
                bis = new BufferedInputStream(http.getInputStream());
                return URLDecoder.decode(new String(IOUtils.toByteArray(bis), CHARSET_UTF8),CHARSET_UTF8);
            } else {
                LOGGER.warn("Http response code:{}", http.getResponseCode());
            }
        } catch (Exception e) {
            LOGGER.error("Error", e);
        } finally {
            IOUtils.closeQuietly(bos);
            IOUtils.closeQuietly(bis);
            if (http != null) http.disconnect();
        }
        return null;
    }
}

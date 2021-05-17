package com.volaid.volaid.util;

import java.nio.charset.Charset;

public class ServiceConstants {

    // GENERAL CONSTANTS
    public static final String UTF_8                = "UTF-8";
    public static final Charset CHARSET_UTF_8       = Charset.forName(UTF_8);
    public static final String PATH_DOMAIN          = "com.volaid.volaid.entity";
    public static final String PATH_REPOSITORIES    = "com.volaid.volaid.repository";
    public static final String TOKEN_PREFIX         = "Bearer ";
    public static final String HEADER_STRING        = "Authorization";
    public static final String SIGN_UP_URL          = "/user/signup";
    public static final String SECRET_KEY           = "SKTGJWTS";

    public static final long EXPIRATION_TIME        = 864_000_000; // 10 days


    // PROJECT CONSTANTS
    public static final String SUCCESS      = "success";
    public static final String OK           = "ok";
    public static final String FAIL         = "fail";
    public static final String RESULT       = "result";
    public static final String MESSAGE      = "message";

    public static final int SUCCESS_CODE    = 0;
    public static final int FAILURE_CODE    = -1;

    public static final int MAX_USER_WARNING_COUNT = 5;


}

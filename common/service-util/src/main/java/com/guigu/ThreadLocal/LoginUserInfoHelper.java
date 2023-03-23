package com.guigu.ThreadLocal;

/**
 * ClassName:LoginUserInfoHelper
 * Package:com.guigu.ThreadLocal
 * Description
 *
 * @Author:@wenxueshi
 * @Create:2023/3/23 - 19:02
 * @Version:v1.0
 */

/**
 * 使用线程ThreadLocal保存当前登录用户信息
 */
public class LoginUserInfoHelper {
    private static ThreadLocal<Long> userId = new ThreadLocal<Long>();
    private static ThreadLocal<String> username = new ThreadLocal<String>();

    public static void setUserId(Long _userId) {
        userId.set(_userId);
    }
    public static Long getUserId() {
        return userId.get();
    }
    public static void removeUserId() {
        userId.remove();
    }
    public static void setUsername(String _username) {
        username.set(_username);
    }
    public static String getUsername() {
        return username.get();
    }
    public static void removeUsername() {
        username.remove();
    }
}

package com.personal.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**通用cookie操作
 * @author 李箎
 */
public class CookieTool {
    /**
     * 批量添加cookie
     * @param response
     * @param keyValue key1 value1 key2 value2...
     */
    public static void batchAddCookie(HttpServletResponse response, String... keyValue) {
        for (int i = 0; i < keyValue.length; i = i + 2) {
            Cookie cookie = new Cookie(keyValue[i], keyValue[i + 1]);
            //cookie.setPath("/");
            cookie.setMaxAge(7 * 24 * 60 * 60);
            //cookie作用域
            //cookie.setDomain(".jsoft.me");
            response.addCookie(cookie);
        }
    }

    /**
     * 批量销毁本地的cookie
     *
     * @param request
     * @param response
     * @param keys 要删除的cookie的key
     */
    public static void batchDeleteCookie(HttpServletRequest request, HttpServletResponse response, String... keys) {
        //遍历要删除的cookie的key
        for (int i = 0; i < keys.length; i++) {
            String key = keys[i];
            //获取所有Cookie
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                //遍历所有Cookie
                for (int j = 0; j < cookies.length; j++) {
                    if (key.equals(cookies[j].getName())) {
                        cookies[j] = new Cookie(key, null);
                        cookies[j].setMaxAge(0);
                        response.addCookie(cookies[j]);
                    }
                }
            }
        }
    }

    /**
     * 检索所有Cookie封装到Map集合
     *
     * @param request
     * @return
     */
    public static Map<String, String> readCookieMap(HttpServletRequest request) {
        Map<String, String> cookieMap = new HashMap<String, String>();
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie.getValue());
            }
        }
        return cookieMap;
    }

    /**
     * 通过Key获取Value
     *
     * @param request
     * @param name Key
     * @return Value
     */
    public static String getCookieValueByName(HttpServletRequest request, String name) {
        Map<String, String> cookieMap = readCookieMap(request);
        if (cookieMap.containsKey(name)) {
            String cookieValue = cookieMap.get(name);
            return cookieValue;
        } else {
            return null;
        }
    }
}

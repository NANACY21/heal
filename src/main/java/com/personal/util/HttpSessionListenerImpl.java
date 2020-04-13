package com.personal.util;

import org.springframework.stereotype.Component;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * https://blog.csdn.net/xichengqc/article/details/87368213
 * https://blog.csdn.net/u014236541/article/details/49786493
 * https://www.cnblogs.com/wcyBlog/p/4657903.html
 * https://www.zhangshengrong.com/p/pDXB0Db3aP/
 * https://www.cnblogs.com/linwenbin/p/11281358.html
 * https://www.jianshu.com/p/e456f3dfa527
 * https://www.cnblogs.com/woshimrf/p/sessionId.html
 * https://www.cnblogs.com/wangxiaoheng/articles/9470031.html
 * @author 李箎
 */
@WebListener
@Component
public class HttpSessionListenerImpl implements HttpSessionListener {
    //session的数量
    public static int onlineCount = 0;

    @Override
    public synchronized void sessionCreated(HttpSessionEvent se) {
        onlineCount++;
        se.getSession().getServletContext().setAttribute("onlineCount", onlineCount);
    }

    @Override
    public synchronized void sessionDestroyed(HttpSessionEvent se) {
        onlineCount--;
        se.getSession().getServletContext().setAttribute("onlineCount", onlineCount);
    }
}

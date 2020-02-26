package com.personal.util;

import com.personal.pojo.Position;

/**
 * 对象池
 *
 * @author 李箎
 */
public class ObjectPool {

    private static Position position = null;

    /**
     * 消费者
     * @return
     */
    public static Position getPosition() {
        return position;
    }

    /**
     * 生产者
     * @param position
     */
    public static void setPosition(Position position) {
        ObjectPool.position = position;
    }
}

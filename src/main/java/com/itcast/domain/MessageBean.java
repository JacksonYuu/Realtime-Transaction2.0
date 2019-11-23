package com.itcast.domain;

import sun.plugin2.message.Message;

/**
 * 与前台通信的消息Bean类
 * @author 拼命三石
 * @version 2.0
 */
public class MessageBean {

    private boolean state;

    private Object message;

    public MessageBean(boolean state, Object message) {

        this.state = state;

        this.message = message;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

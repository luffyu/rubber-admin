package com.rubber.admin.framework.shiro.session.redis;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import org.apache.shiro.session.Session;

import java.io.Serializable;

/**
 * @author luffyu
 * Created on 2019-05-29
 */
public class SendSessionMsg implements Serializable {
    /**
     * 消息的唯一id
     */
    private String id;
    /**
     * 需要删除的session信息
     */
    private Session session;

    public SendSessionMsg() {
    }

    public SendSessionMsg(Session session) {
        this.session = session;
        this.id = IdWorker.getIdStr();

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}

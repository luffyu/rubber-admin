package com.rubber.admin.core.page;

/**
 * @author luffyu
 * Created on 2019-05-27
 */
public enum CompareType {
    /**
     * 比较的关键字
     */
    IN("IN"),
    NOT("NOT"),
    LIKE("LIKE"),
    /**
     * 等于
     */
    EQ("="),
    /**
     * 不等于
     */
    NE("<>"),
    /**
     * 大于
     */
    GT(">"),
    /**
     * 大于等于
     */
    GE(">="),
    /**
     * 小于
     */
    LT("<"),
    /**
     * 小于等于
     */
    LE("<="),
    /**
     * null
     */
    IS_NULL("IS NULL"),
    IS_NOT_NULL("IS NOT NULL")
    ;

    public String key;

    CompareType(final String key) {
        this.key = key;
    }
}

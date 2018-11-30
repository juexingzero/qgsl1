package com.manhui.gsl.jbqgsl.common.enums.news;

import java.util.HashMap;
import java.util.Map;

/**
 * 新闻操作类型
 */
public enum NewsOperatingType {
    commentNums(1,"评论数量"),collectionNums(2,"收藏数量"),viewNums(3,"查看数量"),likeNums(4,"点赞数量"),receiptNums(5,"回执");
    private Integer id;
    private String name;

    NewsOperatingType(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
    public static Map<Integer, String> codeMap = new HashMap<Integer, String>();
    static {
        for ( int i = 0; i < values().length; i++ ) {
            codeMap.put( values()[i].id, values()[i].name );
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

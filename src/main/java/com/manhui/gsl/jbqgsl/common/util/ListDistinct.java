package com.manhui.gsl.jbqgsl.common.util;

import java.util.*;

/**
 *
 * 针对list去重复处理
 **/
public class ListDistinct<T> {
    /**
     * 通过set去重, 不打乱原有list的顺序 list中相同的对象会被去重复
     *
     * @param List<T> list
     * @return List<T>
     */
    public List<T> distinctBySetOrder( List<T> list ) {
        Set<T> set = new HashSet<T>();
        List<T> newList = new ArrayList<T>();
        for ( T t : list ) {
            if ( set.add( t ) ) {
                newList.add( t );
            }
        }
        return newList;
    }

    /**
     * 通过set去重, 顺序可能会乱 list中相同的对象会被去重复
     *
     * @param List<T> list
     * @return List<T>
     */
    public List<T> distinctBySet( List<T> list ) {
        return new ArrayList<T>( new HashSet<T>( list ) );
    }

    /**
     * 通过遍历判断后将赋予新的集合
     *
     * @param List<T>
     * @reutrn List<T>
     */
    public List<T> distinctByANewList( List<T> list ) {
        List<T> newList = new ArrayList<T>();
        for ( T t : list ) {
            if ( !newList.contains( t ) ) {
                newList.add( t );
            }
        }
        return newList;
    }
}

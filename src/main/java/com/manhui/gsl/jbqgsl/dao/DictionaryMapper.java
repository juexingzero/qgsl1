package com.manhui.gsl.jbqgsl.dao;

import org.apache.ibatis.annotations.Mapper;
import com.manhui.gsl.jbqgsl.model.Dictionary;
import java.util.List;

@Mapper
public interface DictionaryMapper {
    /**
     * 查询字典列表
     *
     * @param dictionary
     * @return
     */
    List<Dictionary> queryDictionary( Dictionary dictionary );

    /**
     * 查询字典总数
     *
     * @param dictionary
     * @return
     */
    Integer queryDictionaryCount( Dictionary dictionary );

    /**
     * 添加数据字典
     *
     * @param dictionary
     * @return
     */
    Integer insertDictionary( Dictionary dictionary );

    /**
     * 修改数据字典
     *
     * @param dictionary
     * @return
     */
    Integer updateDictionary( Dictionary dictionary );

    /**
     * 删除据字典
     *
     * @param dictionary
     * @return
     */
    Integer deleteDictionary( Dictionary dictionary );

    /**
     * 修改数据字典
     *
     * @param dictionary
     * @return
     */
    List<Dictionary> queryDictionaryTree( Dictionary dictionary );

    /**
     * 根据条件查询字典值
     *
     * @param dictionary
     * @return
     */
    Dictionary queryDictionaryWhere( Dictionary dictionary );
}

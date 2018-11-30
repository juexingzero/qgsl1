package com.manhui.gsl.jbqgsl.service.web;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.manhui.gsl.jbqgsl.dao.DictionaryMapper;
import com.manhui.gsl.jbqgsl.model.Dictionary;

/**
 * 字典service
 **/
@Service
public class DictionaryImpl implements DictionaryService {
    @Resource
    private DictionaryMapper dictionaryMapper;

    @Override
    public List<Dictionary> queryDictionary( Dictionary dictionary ) {
        return dictionaryMapper.queryDictionary( dictionary );
    }

    @Override
    public Integer queryDictionaryCount( Dictionary dictionary ) {
        return dictionaryMapper.queryDictionaryCount( dictionary );
    }

    @Override
    public Integer insertDictionary( Dictionary dictionary ) {
        return dictionaryMapper.insertDictionary( dictionary );
    }

    @Override
    public Integer updateDictionary( Dictionary dictionary ) {
        return dictionaryMapper.updateDictionary( dictionary );
    }

    @Override
    public Integer deleteDictionary( Dictionary dictionary ) {
        List<Dictionary> dictionaries = dictionaryMapper.queryDictionaryTree( dictionary );
        for ( Dictionary dictionary1 : dictionaries ) {
            if ( dictionary1.getIsSystem() != 1 ) {
                dictionaryMapper.deleteDictionary( dictionary1 );
            }
        }
        return 1;
    }

    @Override
    public Dictionary queryDictionaryWhere( Dictionary dictionary ) {
        return dictionaryMapper.queryDictionaryWhere( dictionary );
    }
}

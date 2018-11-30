package com.manhui.gsl.jbqgsl.controller.web.system;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.manhui.gsl.jbqgsl.model.Dictionary;
import com.manhui.gsl.jbqgsl.service.web.DictionaryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping( "/dict" )
@Controller
public class WebDictionaryController {
    @Resource
    private DictionaryService dictionaryService;

    /**
     * 字典管理页面
     *
     * @return String
     */
    @RequestMapping( "/dictPage" )
    public String dictPage() {
        return "/web/html/dict/dictList";
    }

    /**
     * 添加字典
     *
     * @return String
     */
    @RequestMapping( "/addDict" )
    public String addDict() {
        return "/web/html/dict/addDict";
    }

    /**
     * 选择父级字典
     *
     * @return String
     */
    @RequestMapping( "/dictSelect" )
    public String dictSelect() {
        return "/web/html/dict/dictSelect";
    }

    /**
     * 查询字典列表
     *
     * @return
     */
    @RequestMapping( "/dictionaryList" )
    @ResponseBody
    public Map<String, Object> dictionaryList( Integer pageIndex, Integer pageSize, String key ) {
        Map<String, Object> map = new HashMap<>();
        Dictionary dictionary = new Dictionary();
        dictionary.setPageNo( pageIndex * pageSize );
        dictionary.setPageSize( pageSize );
        dictionary.setWhere( key );
        List<Dictionary> dictionaries = dictionaryService.queryDictionary( dictionary );
        Integer total = dictionaryService.queryDictionaryCount( dictionary );
        if ( !dictionaries.isEmpty() ) {
            map.put( "data", dictionaries );
            map.put( "total", total );
        }
        else {
            map.put( "data", "" );
        }
        return map;
    }

    /**
     * 添加字典
     *
     * @param json
     * @return
     */
    @RequestMapping( "/addDictionary" )
    @ResponseBody
    public Map<String, Object> addDictionary( String json ) {
        Map<String, Object> map = new HashMap<>();
        JSONObject jsonObject = JSON.parseObject( json );
        Dictionary dictionary = jsonObject.toJavaObject( Dictionary.class );
        Integer bools = 0;
        if ( dictionary.getDict_id() != null ) {
            bools = dictionaryService.updateDictionary( dictionary );
        }
        else {
            bools = dictionaryService.insertDictionary( dictionary );
        }
        if ( bools > 0 ) {
            map.put( "code", 200 );
            map.put( "msg", "添加成功" );
        }
        else {
            map.put( "code", 401 );
            map.put( "msg", "添加有误，请重新尝试" );
        }
        return map;
    }

    /**
     * 根据字典编码查询字典
     *
     * @param dict_id
     * @return
     */
    @RequestMapping( "/dictionarySelect" )
    @ResponseBody
    public Map<String, Object> dictionarySelect( Integer dict_id ) {
        Map<String, Object> map = new HashMap<>();
        Dictionary dictionary = new Dictionary();
        dictionary.setDict_id( dict_id );
        Dictionary dictionary1 = dictionaryService.queryDictionaryWhere( dictionary );
        if ( dictionary1.getDict_pid() != null ) {
            Dictionary dictionary2 = new Dictionary();
            dictionary2.setDict_id( dictionary1.getDict_pid() );
            Dictionary dictionary3 = dictionaryService.queryDictionaryWhere( dictionary2 );
            dictionary1.setDict_pidName( dictionary3.getDict_name() );
        }
        if ( dictionary1 != null ) {
            map.put( "code", 200 );
            map.put( "data", dictionary1 );
        }
        else {
            map.put( "code", 401 );
        }
        return map;
    }

    /**
     * 删除数据字典
     *
     * @param dict_id
     * @return
     */
    @RequestMapping( "/deleteDictionary" )
    @ResponseBody
    public Map<String, Object> deleteDictionary( Integer dict_id ) {
        Map<String, Object> map = new HashMap<>();
        Dictionary dictionary = new Dictionary();
        dictionary.setDict_id( dict_id );
        Integer bools = dictionaryService.deleteDictionary( dictionary );
        if ( bools > 0 ) {
            map.put( "code", 200 );
            map.put( "msg", "删除成功" );
        }
        else {
            map.put( "code", 401 );
            map.put( "msg", "删除失败，请重新尝试" );
        }
        return map;
    }
}

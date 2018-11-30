package com.manhui.gsl.jbqgsl.service.web;

import com.github.binarywang.java.emoji.EmojiConverter;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.manhui.gsl.jbqgsl.common.ResultMessage;
import com.manhui.gsl.jbqgsl.common.enums.*;
import com.manhui.gsl.jbqgsl.common.enums.news.NewsCommentState;
import com.manhui.gsl.jbqgsl.common.enums.news.NewsOperatingType;
import com.manhui.gsl.jbqgsl.common.util.DateUtil;
import com.manhui.gsl.jbqgsl.dao.NewsOperatingMapper;
import com.manhui.gsl.jbqgsl.model.*;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 新闻操作管理（评论，点赞，收藏）
 */
@Service
public class NewsOperatingServiceImpl implements NewsOperatingService {
    String strFomat = "yyyy-MM-dd HH:mm:ss";
    /**
     * 表情转码
     */
    private static EmojiConverter emojiConverter = EmojiConverter.getInstance();
    @Resource
    private NewsOperatingMapper newsOperatingMapper;
    @Autowired
    private NewsService newsService;

    @Override
    public ResultMessage saveNewsOperating(NewsOperating operating) throws Exception {

        //验证信息是否重复
        if(operating.getType().equals(2) || operating.getType().equals(4)){
            List<NewsOperating> operatings = newsOperatingMapper.getUserCollectionNews(operating);
            if(operatings != null && !operatings.isEmpty()){
                return  new ResultMessage("不能重复操作");
            }
        }
        ResultMessage message =  new ResultMessage();
        if(!operating.getType().equals(NewsOperatingType.receiptNums.getId())){
            //类型 不是回执才修改
            News news = new News();
            news.setNewsId(operating.getNewsId());
            if(operating.getType().equals(NewsOperatingType.commentNums.getId())){
                news.setCommentNums(1);
            }else if(operating.getType().equals(NewsOperatingType.collectionNums.getId())){
                news.setCollectionNums(1);
            }else if(operating.getType().equals(NewsOperatingType.likeNums.getId())){
                news.setLikeNums(1);
            }
            //修改点赞，收藏数量
            message = newsService.updateNews(news,null);
        }


        if(message.getState().equals(0)){
            //保存操作数据
          //如果是点赞蒋不保存a
            if(!operating.getType().equals(NewsOperatingType.likeNums.getId())) {
                String strTime = new DateTime().toString(strFomat);
                operating.setCreateTime(strTime);
                operating.setUpdateTime(strTime);
                operating.setState(1);
                newsOperatingMapper.save(operating);
                message = new ResultMessage();
                //返回新增数据id
                message.setData(operating.getOperatingId());
            }
        }
        return message;
    }

    /**
     * 查询用户 收藏新闻
     * @return
     */
    @Override
    public ResultMessage getUserCollectionNews(NewsOperating operating) throws ParseException {
        operating.setType(NewsOperatingType.collectionNums.getId());
        PageHelper.startPage( operating.getPageNo(), operating.getPageSize() );//当前第几页，每页显示多少条
        List<NewsOperating> dataList = newsOperatingMapper.getUserCollectionNews(operating);
        PageInfo<NewsOperating> info;
        if(dataList != null && !dataList.isEmpty()){
            for(NewsOperating e : dataList){
                e.setCreateTime(DateUtil.getConversionTime(e.getCreateTime(),DateUtil.sdf));
            }
            info = new PageInfo<NewsOperating>( dataList );
        }else {
            info = new PageInfo<NewsOperating>();
        }
        return new ResultMessage(info);
    }

    /**
     * 取消用户 收藏新闻
     * @param operating
     * @return
     */
    @Override
    public ResultMessage cancelUserCollection(NewsOperating operating) {
        List<NewsOperating> dataList = newsOperatingMapper.getUserCollectionNews(operating);
        if(dataList == null || dataList.isEmpty()){
            return new ResultMessage("数据不存在或已经删除，请刷新重试！");
        }
        operating = dataList.get(0);
        operating.setState(2);
        operating.setUpdateTime(new DateTime().toString(strFomat));
        int total = newsOperatingMapper.updateById(operating);
        if(total == 0){
            return new ResultMessage("系统错误，请刷新重试！");
        }
        return new ResultMessage();
    }

    /**
     * * 查询 新闻评论列表
     * @param operating
     * @return
     */
    @Override
    public Map<String, Object> queryCommentList(NewsOperating operating) throws ParseException {

        Map<String,Object> dataMap = new HashMap<>();
        //查询 评论数据列表
        List<NewsOperating>  commentList = newsOperatingMapper.queryCommentList(operating);
        String content = "";
        if(commentList != null && !commentList.isEmpty()){
            //转换时间
            for(NewsOperating e : commentList){
                e.setCreateTime(DateUtil.getConversionTime(e.getCreateTime(),DateUtil.sdf));

                //转码表情
                content = emojiConverter.toUnicode(e.getContent());
                e.setContent(content);
            }
        }
        dataMap.put("commentList",commentList);
        return dataMap;
    }

    @Override
    public Map<String, Object> getUserNewsOperatingList(NewsOperating operating) {
        Map<String,Object> dataMap = new HashMap<>();
        Map<Integer,String> enumsMap = NewsOperatingType.codeMap;
        //查询人员 对 新闻的操作数据
        List<NewsOperating> dataList = newsOperatingMapper.getUserNewsOperatingList(operating);
        if(dataList != null && !dataList.isEmpty()){
            for(NewsOperating e : dataList){
                dataMap.put(e.getType()+"",enumsMap.get(e.getType()));
            }
        }
        return  dataMap;
    }

    /**
     * 查询新闻 互动内容
     * @param operating
     * @return
     */
    @Override
    public PageInfo<NewsOperating> queryInteractiveDataList(NewsOperating operating) {
        PageHelper.startPage( operating.getPageNo(), operating.getPageSize() );//当前第几页，每页显示多少条
        operating.setState(1);
        List<NewsOperating> newsList = newsOperatingMapper.queryCommentList( operating );
        PageInfo<NewsOperating> info;
        if(newsList != null && !newsList.isEmpty()){
            for(NewsOperating e : newsList){
                e.setCommentStateStr(NewsCommentState.codeMap.get(e.getCommentState()));
            }
            info = new PageInfo<NewsOperating>( newsList );
        }else{
            info = new PageInfo<NewsOperating>();
        }
        return info;
    }

    /**
     * 删除数据
     * @param operating
     * @return
     */
    @Override
    public ResultMessage del(NewsOperating operating,String userid) throws Exception {
        NewsOperating entity = newsOperatingMapper.getNewsOperatingById(operating.getOperatingId());
        if(entity == null){
            return new ResultMessage("数据异常，请刷新后重试");
        }
        entity.setState(2);
        entity.setUpdateTime(new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
        int count = newsOperatingMapper.updateById(entity);
        if(count == 0){
            return new ResultMessage("数据异常，请刷新后重试");
        }
        ResultMessage message = this.updateNewsInteractive(entity.getNewsId(),userid,entity.getType());

        return message;
    }

    @Override
    public ResultMessage updateById(NewsOperating operating) {
        operating.setUpdateTime(new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
        int count = newsOperatingMapper.updateById(operating);
        if(count == 0){
            return new ResultMessage("数据异常，请刷新后重试");
        }
        return new ResultMessage();
    }

    /**
     * 查询回执 数据
     * @param operating
     * @return
     */
    @Override
    public PageInfo<NewsOperating> queryReceiptDataList(NewsOperating operating) {
        PageHelper.startPage( operating.getPageNo(), operating.getPageSize() );//当前第几页，每页显示多少条
        operating.setState(1);
        List<NewsOperating> newsList = newsOperatingMapper.queryReceiptDataList( operating );
        //转换类型
        if(newsList != null && !newsList.isEmpty()){
            Map<Integer,String> receiptMap = ReceiptType.codeMap;
            for (NewsOperating e : newsList){
                e.setTypeStr(receiptMap.get(e.getType()));
            }
        }
        PageInfo<NewsOperating> info;
        if(newsList != null && !newsList.isEmpty()){
            info = new PageInfo<NewsOperating>( newsList );
        }else{
            info = new PageInfo<NewsOperating>();
        }
        return info;
    }

    /**
     * 查询评论详情数据
     * @param operatingIds
     * @return
     */
    @Override
    public List<NewsOperating> queryCommentInfo(List<Integer> operatingIds) {
        List<NewsOperating> operatingList = newsOperatingMapper.queryCommentInfo(operatingIds);
        return operatingList;
    }

    /**
     * 批量查询
     * @param ids
     * @return
     */
    @Override
    public List<NewsOperating> getByOperatingIds(String[] ids) {
        List<Integer> idList = new ArrayList<>();
        if(ids != null && ids.length>0){
            for(int i=0;i<ids.length;i++){
                idList.add(Integer.parseInt(ids[i]));
            }
        }
        return newsOperatingMapper.queryCommentInfo(idList);
    }

    /**
     * 取消 收藏，点赞，评论时，调用。修改新闻的收藏，点赞，评论数量
     * @param newsId
     * @param userId
     * @param operatingType 评论，收藏，点赞 等类型
     * @return
     * @throws Exception
     */
    public ResultMessage updateNewsInteractive(Integer newsId,String userId,Integer operatingType) throws Exception {
        News news = new News();
        news.setNewsId(newsId);
        if(operatingType.equals(NewsOperatingType.collectionNums.getId())){
            news.setCollectionNums(-1);
        }else if(operatingType.equals(NewsOperatingType.likeNums.getId())){
            news.setLikeNums(-1);
        }else if(operatingType.equals(NewsOperatingType.commentNums.getId())){
            news.setCommentNums(-1);
        }else if(operatingType.equals(NewsOperatingType.receiptNums.getId())){
            news.setReceiptNums(-1);
        }else{
            throw new RuntimeException("数据异常，请刷新后重试");
        }
        ResultMessage message = newsService.updateNews(news,userId);

        return message;
    }
}

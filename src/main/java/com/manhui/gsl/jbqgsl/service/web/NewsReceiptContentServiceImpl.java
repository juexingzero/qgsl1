package com.manhui.gsl.jbqgsl.service.web;

import com.github.binarywang.java.emoji.EmojiConverter;
import com.manhui.gsl.jbqgsl.common.ResultMessage;
import com.manhui.gsl.jbqgsl.common.enums.news.NewsOperatingType;
import com.manhui.gsl.jbqgsl.dao.NewsReceiptContentMapper;
import com.manhui.gsl.jbqgsl.model.NewsOperating;
import com.manhui.gsl.jbqgsl.model.NewsReceiptContent;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 新闻回执内容管理
 */
@Service
public class NewsReceiptContentServiceImpl implements NewsReceiptContentService {
    String strFomat = "yyyy-MM-dd HH:mm:ss";
    /**
     * 表情转码
     */
    private static EmojiConverter emojiConverter = EmojiConverter.getInstance();
    @Resource
    private NewsReceiptContentMapper newsReceiptContentMapper;
    @Resource
    private NewsOperatingService newsOperatingService;

    /**
     * 保存回执内容
     * @param receiptContent
     * @return
     */
    @Override
    public ResultMessage saveReceipt(NewsReceiptContent receiptContent) throws Exception {
        NewsOperating operating = new NewsOperating();
        operating.setType(NewsOperatingType.receiptNums.getId());
        operating.setUserId(receiptContent.getUserId());
        operating.setUserName(receiptContent.getUserName());
        operating.setNewsId(receiptContent.getNewsId());
        operating.setNewsName(receiptContent.getNewsName());

        ResultMessage message = newsOperatingService.saveNewsOperating(operating);
        if(message.getState().equals(0)){
            receiptContent.setOperatingId((int)message.getData());
            newsReceiptContentMapper.save(receiptContent);
        }
        return message;
    }

    /**
     * 查询人员 回执内容
     * @param receiptContent
     * @return
     */
    @Override
    public ResultMessage queryUserReceipt(NewsReceiptContent receiptContent) {
        List<NewsReceiptContent> contents = newsReceiptContentMapper.queryReceiptList(receiptContent);
        ResultMessage message = new ResultMessage();
        if(contents != null && !contents.isEmpty()){
            message.setData(contents);
        }else{
            message = new ResultMessage("数据不存在");
        }
        return message;
    }

    /**
     *
     * @param receiptContent
     * @return
     */
    @Override
    public ResultMessage updateUserReceipt(NewsReceiptContent receiptContent) {
        NewsReceiptContent entity = newsReceiptContentMapper.queryById(receiptContent.getReceiptId());
        if(entity == null){
            return new ResultMessage("数据不存在，请刷新后重试");
        }
        NewsOperating operating = new NewsOperating();
        operating.setOperatingId(entity.getOperatingId());
        ResultMessage message = newsOperatingService.updateById(operating);

        if(message.getState().equals(0)){
            //修改数据
            int count = newsReceiptContentMapper.updateById(receiptContent);
            if(count == 0){
                return new ResultMessage("数据异常，请刷新后重试");
            }
        }else{
            return new ResultMessage("数据异常，请刷新后重试");
        }

        return new ResultMessage();
    }
}

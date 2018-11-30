package com.manhui.gsl.jbqgsl.controller.web.messageManager;

import com.github.pagehelper.PageInfo;
import com.manhui.gsl.jbqgsl.common.JpushClientUtil;
import com.manhui.gsl.jbqgsl.common.ResultMessage;
import com.manhui.gsl.jbqgsl.common.enums.news.NewsCommentState;
import com.manhui.gsl.jbqgsl.common.enums.news.NewsOperatingType;
import com.manhui.gsl.jbqgsl.model.NewsOperating;
import com.manhui.gsl.jbqgsl.service.web.NewsOperatingService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.manhui.gsl.jbqgsl.controller.BaseController.PARENT_URL_WEB;

/**
 * 新闻评论管理
 */
@RequestMapping( WebNewsCommentManagerController.ROOT_URL )
@Controller
public class WebNewsCommentManagerController {
    public static final String    ROOT_URL = PARENT_URL_WEB + "/commentManager";
    @Resource
    private NewsOperatingService newsOperatingService;
    private static String jpush = "Jpush_";
    @RequestMapping( "/commentDataPage" )
    public String commentDataPage() throws Exception {
        return "/web/html/commentManager/newsCommentManagerPage";
    }

    /**
     * 查询新闻 评论内容 列表
     */
    @RequestMapping( "/queryInteractiveData" )
    @ResponseBody
    public Map<String, Object> queryInteractiveData(NewsOperating operating ) {
        operating.setType(NewsOperatingType.commentNums.getId());
        PageInfo<NewsOperating> pageInfo = newsOperatingService.queryInteractiveDataList(operating);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put( "total", pageInfo.getTotal() );
        dataMap.put( "data", pageInfo.getList() );
        return dataMap;
    }

    /**
     * 同意评论内容
     * @param operatingIds
     * @return
     */
    @RequestMapping(value = "/passComment",method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage passComment(String operatingIds) {
        if(StringUtils.isBlank(operatingIds)){
            throw new RuntimeException("参数不能为空");
        }
        String ids[] = operatingIds.split(",");
        ResultMessage result = null;
        NewsOperating operating;
        for(int i=0;i<ids.length;i++){
            operating = new NewsOperating();
            operating.setType(NewsOperatingType.commentNums.getId());
            operating.setCommentState(NewsCommentState.TWO.getId());
            operating.setOperatingId(Integer.parseInt(ids[i]));
            result = newsOperatingService.updateById(operating);
        }

        return result;
    }

    /**
     * 不合格原因 选择页面
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping( "/vetoCommentData" )
    public String vetoCommentData(ModelMap model) throws Exception {

        return "/web/html/commentManager/vetoCommentData";
    }
    /**
     * 否决 不同意评论内容
     * @param operatingIds
     * @return
     */
    @RequestMapping(value = "/vetoComment",method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage vetoComment(String operatingIds,String noReason) {
        if(StringUtils.isBlank(operatingIds)){
            throw new RuntimeException("参数不能为空!");
        }
        String ids[] = operatingIds.split(",");
        NewsOperating operating;
        ResultMessage result = null;
        for(int i=0;i<ids.length;i++){
            operating = new NewsOperating();
            operating.setType(NewsOperatingType.commentNums.getId());
            operating.setCommentState(NewsCommentState.THREE.getId());
            operating.setOperatingId(Integer.parseInt(ids[i]));
            operating.setNoReason(noReason);
            result = newsOperatingService.updateById(operating);
        }
        //发送消息推送,提醒评论信息不对
        List<NewsOperating> operatings = newsOperatingService.getByOperatingIds(ids);
        if(operatings != null && !operatings.isEmpty()){
            String alias[];
            NewsOperating e;
            for(int i=0;i<operatings.size();i++){
                alias = new String[1];
                e = operatings.get(i);
                alias[i] = jpush+e.getUserId();
                JpushClientUtil.pushMsg(alias,"江北区工商联","你对新闻："+e.getNewsName()+"评论信息铭感，特此警告!");
            }
        }

        return result;
    }
    /**
     * 查询详情
     * @return
     * @throws Exception
     */
    @RequestMapping( "/queryDetails" )
    public String queryDetails(String operatingIds, ModelMap model) throws Exception {
        if(StringUtils.isBlank(operatingIds)){
            throw new RuntimeException("参数不能为空！");
        }
        List<Integer> operatingIdList = new ArrayList<>();
        String ids[] = operatingIds.split(",");
        for (int i=0;i<ids.length;i++){
            operatingIdList.add(Integer.valueOf(ids[i]));
        }
        List<NewsOperating> operatingList = newsOperatingService.queryCommentInfo(operatingIdList);
        model.addAttribute("operatingList",operatingList);
        model.addAttribute("operatingIds",operatingIds);
        model.addAttribute("query","true");
        return "/web/html/commentManager/queryDetailsPage";
    }

    /**
     * 审核 评论内容
     * @param operatingIds
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping( "/reviewComment" )
    public String reviewComment(String operatingIds, ModelMap model) throws Exception {
        if(StringUtils.isBlank(operatingIds)){
            throw new RuntimeException("参数不能为空！");
        }
        List<Integer> operatingIdList = new ArrayList<>();
        String ids[] = operatingIds.split(",");
        for (int i=0;i<ids.length;i++){
            operatingIdList.add(Integer.valueOf(ids[i]));
        }
        List<NewsOperating> operatingList = newsOperatingService.queryCommentInfo(operatingIdList);
        model.addAttribute("operatingList",operatingList);
        model.addAttribute("operatingIds",operatingIds);
        return "/web/html/commentManager/queryDetailsPage";
    }
    /**
     * 删除评论内容
     * @return
     * @throws Exception
     */
    @RequestMapping( "/del" )
    @ResponseBody
    public ResultMessage del(String operatingIds) throws Exception {
        if(StringUtils.isBlank(operatingIds)){
            throw new RuntimeException("参数不能为空!");
        }
        String ids[] = operatingIds.split(",");
        NewsOperating operating;
        ResultMessage result = null;
        for(int i=0;i<ids.length;i++){
            operating = new NewsOperating();
            operating.setType(NewsOperatingType.commentNums.getId());
            operating.setState(2);//删除状态
            operating.setOperatingId(Integer.parseInt(ids[i]));
            result = newsOperatingService.updateById(operating);
        }
        return result;
    }

}

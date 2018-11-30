package com.manhui.gsl.jbqgsl.service.web.commerce.impl;

import com.manhui.gsl.jbqgsl.common.util.UUIDUtil;
import com.manhui.gsl.jbqgsl.controller.web.commerce.LeadershipResult;
import com.manhui.gsl.jbqgsl.dao.web.commerce.LeadershipMapper;
import com.manhui.gsl.jbqgsl.model.commerce.MemberJgShldbz;
import com.manhui.gsl.jbqgsl.service.web.commerce.ILeadershipService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @类名称 CommerceManagerServiceImpl.java
 * @类描述 商会管理
 * @作者 kevin kwmo1005@163.com
 * @创建时间 2018年10月25日 上午10:43:31
 * @版本 1.00
 *
 * @修改记录
 *
 *       <pre>
 *     版本		 修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	kevin 	 2018年10月25日                创建
 *     ----------------------------------------------
 *       </pre>
 */
@Service
public class LeadershipServiceImpl implements ILeadershipService {
    private static final Logger logger = LoggerFactory.getLogger( CommerceManagerServiceImpl.class );

    @Resource
    private LeadershipMapper leadershipMapper;

    @Override
    public List<LeadershipResult> queryLeadershipList(Map<String,Object> data) {
        return leadershipMapper.queryLeadershipList(data);
    }

    @Override
    public Integer queryLeadershipListSize(String SHBMID) {
        return leadershipMapper.queryLeadershipListSize(SHBMID);
    }

    @Override
    public List<Map<String, Object>> queryLeadershipDetailList(String SHBMID) {
        return leadershipMapper.queryLeadershipDetailList(SHBMID);
    }

    @Override
    public List<Map<String,Object>> queryLeadership(Map<String,Object> data) {
        return leadershipMapper.queryLeadership(data);
    }

    @Override
    public List<Map<String, Object>> queryLeaderMemberInfo(Map<String, Object> data) {
        return leadershipMapper.queryLeaderMemberInfo(data);
    }

    @Override
    public Integer saveLeadership(MemberJgShldbz data) {
        logger.info("--------保存开始----");
        data.setID(UUIDUtil.getUUID());
        Integer flag = 0;
        //检查人员是否存在于主席、党组书记、秘书长三个职位中
        if("1".equals(data.getSHZWLX()) || "2".equals(data.getSHZWLX())  || "4".equals(data.getSHZWLX())){
           Map<String,Object> result =  leadershipMapper.queryLeaderCheck(data);
           if(result != null && result.size() > 0){
                flag = 0;
                return flag;
           }
        }
        //检查人员是否同一届选择多个职务
        Map<String,Object> result = leadershipMapper.queryLeaderMemberCheck(data);
        if(result != null && result.size() > 0){
            flag = 2;
            return flag;
        }
        flag = leadershipMapper.saveLeadership(data);
        logger.info("--------保存结束----");
        return flag;
    }

    @Override
    public Integer editLeadership(MemberJgShldbz data) {
        logger.info("--------编辑开始----");
        data.setID(UUIDUtil.getUUID());
        Integer flag = leadershipMapper.editLeadership(data);
        logger.info("--------编辑结束----");
        return flag;
    }

    @Override
    public Integer deleteLeadership(String ID) {
        return leadershipMapper.deleteLeadership(ID);
    }
}

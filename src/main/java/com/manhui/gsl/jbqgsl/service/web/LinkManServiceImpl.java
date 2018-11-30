package com.manhui.gsl.jbqgsl.service.web;

import static java.util.Comparator.comparing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.manhui.gsl.jbqgsl.controller.app.linkman.DeptResult;
import com.manhui.gsl.jbqgsl.controller.app.linkman.LinkManResult;
import com.manhui.gsl.jbqgsl.controller.app.linkman.PositionResult;
import com.manhui.gsl.jbqgsl.controller.app.linkman.TelephoneRecordResult;
import com.manhui.gsl.jbqgsl.dao.LinkManMapper;
import com.manhui.gsl.jbqgsl.model.Dept;
import com.manhui.gsl.jbqgsl.model.News;
import com.manhui.gsl.jbqgsl.model.Position;
import com.manhui.gsl.jbqgsl.model.topicevaluate.TelephoneRecord;

@Service
public class LinkManServiceImpl implements ILinkManService {
    @Resource
    private LinkManMapper linkManMapper;

    /**
     * @方法名称 getDeptAndPositionList
     * @功能描述 获取部门及岗位列表，用于APP接口
     * @作者 kevin
     * @创建时间 2018年8月22日 下午2:43:22
     * @return
     */
    @Override
    public Map<String, Object> getDeptAndPositionList() {
        Map<String, Object> resp = new HashMap<>();
        List<Dept> pDeptList = linkManMapper.getDeptList( "1", "0" );//一级部门
        if ( pDeptList != null && pDeptList.size() > 0 ) {
            //机构（父级部门），暂时这样处理，后续优化
            List<DeptResult> pResultList = new ArrayList<>();
            for ( Dept pDept : pDeptList ) {
                DeptResult pdr = new DeptResult();
                pdr.setDept_id( pDept.getDept_id() );
                pdr.setDept_name( pDept.getDept_name() );
                pdr.setDept_order( pDept.getOrder_no() );
                List<Dept> deptList = linkManMapper.getDeptList( pDept.getDept_id(), null );//二级部门
                List<Position> positionList = linkManMapper.getPositionList();//所有的岗位
                //部门
                if ( deptList != null && deptList.size() > 0 ) {
                    List<DeptResult> drList = new ArrayList<>();
                    for ( Dept dept : deptList ) {
                        DeptResult dr = new DeptResult();
                        dr.setDept_id( dept.getDept_id() );
                        dr.setDept_name( dept.getDept_name() );
                        dr.setDept_order( dept.getOrder_no() );
                        //岗位
                        if ( positionList != null && positionList.size() > 0 ) {
                            List<PositionResult> prList = new ArrayList<>();
                            for ( Position position : positionList ) {
                                if ( position.getDept_id().equals( dept.getDept_id() ) ) {
                                    PositionResult pr = new PositionResult();
                                    pr.setPosition_id( position.getPosition_id() );
                                    pr.setPosition_name( position.getPosition_name() );
                                    pr.setPosition_order( position.getOrder_no() );
                                    pr.setLinkManList( linkManMapper.getLinkManByPositionList( pr.getPosition_id() ) );
                                    prList.add( pr );
                                }
                            }
                            dr.setPositionList( prList );
                        }
                        drList.add( dr );
                        pdr.setDeptList( drList );
                    }
                }
                pResultList.add( pdr );
            }
            resp.put( "pDeptList", pResultList );
        }
        return resp;
    }
    /**
     * @方法名称 getDeptList 1.0.2版本
     * @功能描述 获取部门及岗位列表，用于APP接口
     * @作者 LiuBin
     * @创建时间 2018年8月22日 下午2:43:22
     * @return
     */
    @Override
    public Map<String, Object> getDeptList() {
    	Map<String, Object> resp = new HashMap<>();
    	List<Dept> pDeptList = linkManMapper.getDeptList( "1", "0" );//一级部门
    	if ( pDeptList != null && pDeptList.size() > 0 ) {
    		//机构（父级部门），暂时这样处理，后续优化
    		List<DeptResult> pResultList = new ArrayList<>();
    		for ( Dept pDept : pDeptList ) {
    			DeptResult pdr = new DeptResult();
    			pdr.setDept_id( pDept.getDept_id() );
    			pdr.setDept_name( pDept.getDept_name() );
    			pdr.setDept_order( pDept.getOrder_no() );
    			List<Dept> deptList = linkManMapper.getDeptList( pDept.getDept_id(), null );//二级部门
    			List<Position> positionList = linkManMapper.getPositionList();//所有的岗位
    			//部门
    			if ( deptList != null && deptList.size() > 0 ) {
    				List<DeptResult> drList = new ArrayList<>();
    				for ( Dept dept : deptList ) {
    					DeptResult dr = new DeptResult();
    					dr.setDept_id( dept.getDept_id() );
    					dr.setDept_name( dept.getDept_name() );
    					dr.setDept_order( dept.getOrder_no() );
    					drList.add( dr );
    					pdr.setDeptList( drList );
    				}
    			}
    			pResultList.add( pdr );
    		}
    		resp.put( "pDeptList", pResultList );
    	}
    	return resp;
    }
    
    
    
    /**
     * @方法名称 getLinkManByPositionList
     * @功能描述 获取岗位下对应的联系人列表，用于APP接口
     * @作者 kevin
     * @创建时间 2018年8月22日 下午4:32:30
     * @param position_id
     * @return
     */
    @Override
    public Map<String, Object> getLinkManByPositionList( String position_id ) {
        Map<String, Object> resp = new HashMap<>();
        List<LinkManResult> lmrList = linkManMapper.getLinkManByPositionList( position_id );
        resp.put( "linkManList", lmrList );
        return resp;
    }


	/**
	 * 获取到部门下面的岗位以及联系人
	 */
//	@Override
//	public Map<String, Object> getPositionAndUser(String dept_id) {
//		Map<String, Object> resp = new HashMap<>();
//		List<Position> positionList = linkManMapper.getPositionList();//所有的岗位
//		 Dept dept = linkManMapper.queryDeptInfo(dept_id); //查询部门详细信息--app端展示部门名称
//		  if ( positionList != null && positionList.size() > 0 ) {
//              List<PositionResult> prList = new ArrayList<>();
//              for ( Position position : positionList ) {
//                  if ( position.getDept_id().equals( dept_id ) ) {
//                      PositionResult pr = new PositionResult();
//                      pr.setPosition_id( position.getPosition_id() );
//                      pr.setPosition_name( position.getPosition_name() );
//                      pr.setPosition_order( position.getOrder_no() );
//                      pr.setDept_name(dept.getDept_name());
//                      List<LinkManResult> linkManByPositionList = linkManMapper.getLinkManByPositionList( pr.getPosition_id() );
//	                  if(linkManByPositionList !=null && linkManByPositionList.size()>0) {
//	                     for (LinkManResult linkMan : linkManByPositionList) {
//	                    	 linkMan.setPosition_id(pr.getPosition_id());
//	                    	 linkMan.setPosition_name(pr.getPosition_name());
//	                    	 linkMan.setDept_name(pr.getDept_name());
//	                     }
//	                    	 
//	                  }
//                      pr.setLinkManList( linkManByPositionList );
//                      
//                      prList.add( pr );
//                  }
//              }
//              resp.put("posAndUser", prList);
//          }
//		return resp;
//	}
	
	/**
	 * 获取到部门下面的岗位以及联系人
	 */
	@Override
	public Map<String, Object> getPositionAndUser(String dept_id) {
		Map<String, Object> resp = new HashMap<>();
		List<String> positionIdsList = new ArrayList<>();
		List<LinkManResult> linkManByPositionList = new ArrayList<>();
		List<Position> positionList = linkManMapper.getPositionList();//所有的岗位
		Dept dept = linkManMapper.queryDeptInfo(dept_id); //查询部门详细信息--app端展示部门名称
		PositionResult pr = null;
		if ( positionList != null && positionList.size() > 0 ) {
			List<LinkManResult> prList = new ArrayList<>();
			for ( Position position : positionList ) {
				if ( position.getDept_id().equals( dept_id ) ) {
					pr = new PositionResult();
					pr.setPosition_id( position.getPosition_id() );
					pr.setPosition_name( position.getPosition_name() );
					pr.setPosition_order( position.getOrder_no() );
					pr.setDept_name(dept.getDept_name());
					positionIdsList.add(pr.getPosition_id());
				}
			}
			if(positionIdsList !=null && !positionIdsList.isEmpty()) {
				linkManByPositionList = linkManMapper.getLinkManByPositionList2( positionIdsList );
				if(linkManByPositionList !=null && linkManByPositionList.size()>0) {
					for (int i = 0; i < linkManByPositionList.size(); i++) {
						LinkManResult linkMan = linkManByPositionList.get(i);
						linkMan.setDept_name(dept.getDept_name());
					}
					
				}
			}
			resp.put("posAndUser", linkManByPositionList);
		}
		  
		
		return resp;
	}
	@Override
	public List<LinkManResult> queryLinkManList(TelephoneRecord tr) {
		List<LinkManResult> queryLinkManList = linkManMapper.queryLinkManList(tr);
		return queryLinkManList;
	}
}

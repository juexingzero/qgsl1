package com.manhui.gsl.jbqgsl.model;

import lombok.Data;

/**
 * @Title: MeetingVoteOptions.java
 * @Package com.manhui.gsl.jbqgsl.model
 * @Description: TODO(投票议题选项表)
 * @author WangSheng
 * @date 2018年10月17日
 * @version V1.0
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */
@Data
public class MeetingVoteOptions {

	private String options_id;	//议题选项表id
	private String vote_id;		//会议投票id
	private String options;		//选项
	private String sorting;		//排序
	private String create_time;	//创建时间
	private String update_time;	//修改时间
	
}

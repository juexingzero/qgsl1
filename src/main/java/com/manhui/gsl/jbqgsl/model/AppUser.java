package com.manhui.gsl.jbqgsl.model;

import lombok.Data;

/**
 * @Title: AppUser.java
 * @Package com.manhui.gsl.jbqgsl.model
 * @Description: TODO(App端用户实体类)
 * @author LiuBin
 * @date 2018年8月13日
 * @version V1.0
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */
@Data
public class AppUser {
    private String  user_id;        // 用户id
    private String  institution_id; //机构id
    private String  login_username; // 登陆账号
    private String  login_password; // 登陆密码
    private String  user_name;      // 用户名称
    private String  user_sex;       // 用户性别(0：男，1：女，默认0)
    private String  user_phone;     // 用户手机
    private String  user_email;     // 用户邮箱
    private String  head_img;       // 用户头像
    private String  qr_code_img;    // 二维码
    private String  user_type;      // 用户类型(1：普通用户，2：双向评价用户，默认1)
    private String  is_public;      // 是否公开账户信息(0：否，1：是，默认1)
    private Integer login_num;      // 登录次数
    private String  last_time;      // 上次登录时间
    private String  create_time;    // 创建时间
    private String  update_time;    // 修改时间
    private String  del_flag;    // 是否删除 0:未删除 1:已删除
    private String phoneId; //手机id
    //keywords
    private Integer pageNo;         //开始页数
    private Integer pageSize;       //查询总数
    private String institution_name;       //机构名称
}

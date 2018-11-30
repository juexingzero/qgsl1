package com.manhui.gsl.jbqgsl.service.app.membermanager;

import com.manhui.gsl.jbqgsl.common.util.PageModel;
import lombok.Data;

@Data
public class ParamModel extends PageModel {
    private String joinObjId;//商会id
    private String position;//职务 参照商会职务字典
    private String contact;//联系人姓名查询
    private String user_phone;//联系人电话查询
}

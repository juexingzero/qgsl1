package com.manhui.gsl.jbqgsl.common.enums.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 人员信息 枚举
 */
public class PeopleEnum {

    /**
     * 人员角色类型
     */
    public enum peopleRoleType{
        ONE("RYJSLX_01","未认证用户"),TWO("RYJSLX_02","商会会员"),THREE("RYJSLX_03","商会联系人"),
        FOUR("RYJSLX_04","工商联部室"),FIVE("RYJSLX_05","工商联领导"),SIX("RYJSLX_06","区级部门"),SEVEN("RYJSLX_07","街镇部门");

        private String code;
        private String name;

        peopleRoleType(String code,String name){
            this.code = code;
            this.name = name;
        }


        public static Map<String,String> codeMap = new HashMap<>();

        static {
            for(int i=0; i<values().length; i++){
                codeMap.put(values()[i].code,values()[i].name);
            }
        }


        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}

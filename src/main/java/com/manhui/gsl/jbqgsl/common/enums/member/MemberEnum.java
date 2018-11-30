package com.manhui.gsl.jbqgsl.common.enums.member;

import java.util.HashMap;
import java.util.Map;

/**
 * 会员入会 枚举
 */
public class MemberEnum {

    /**
     * 会员类型
     */
    public enum mmberType{
        INDIVIDUAL("HYLX-01","个人会员"),GROUP("HYLX-02","团体会员"),ENTERPRISE("HYLX-03","企业会员");
        private String code;
        private String name;

        mmberType(String code,String name){
            this.code = code;
            this.name = name;
        }
        public static Map<String, String> codeMap = new HashMap<String, String>();

        static {
            for (int i = 0; i < values().length; i++) {
                codeMap.put(values()[i].code, values()[i].name);
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

    /**
     * 会员数据状态
     */
    public enum mmberState{
        ADD("HYSP-00","新增"),PENDING("HYSP-01","待审批"),APPROVED("HYSP-02","已审批"),FAILED("HYSP-03","审批拒绝"),DEL("HYSP-04","删除");
        private String code;
        private String name;

        mmberState(String code,String name){
            this.code = code;
            this.name = name;
        }
        public static Map<String, String> codeMap = new HashMap<String, String>();

        static {
            for (int i = 0; i < values().length; i++) {
                codeMap.put(values()[i].code, values()[i].name);
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

    /**\
     * 审核类型
     */
    public enum checkType{
        BASE("HYSPZL-01","基本信息"),ZW("HYSPZL-02","商会职务"),RY("HYSPZL-03","主要荣誉"),FR("HYSPZL-04","法人信息"),
        SJ("HYSPZL-05","授奖情况"),QYJS("HYSPZL-06","企业介绍"),STJS("HYSPZL-07","社团介绍"),ZJXX("HYSPZL-08","证件信息");
        private String code;
        private String name;

        checkType(String code,String name){
            this.code = code;
            this.name = name;
        }
        public static Map<String, String> codeMap = new HashMap<String, String>();

        static {
            for (int i = 0; i < values().length; i++) {
                codeMap.put(values()[i].code, values()[i].name);
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

    /**
     * 荣誉级别
     */
    public enum honorLevel{
        COUNTY_LEVEL("RYJB-04","县级"),MUNICIPAL_LEVEL("RYJB-03","市级"),PROVINCIAL("RYJB-02","省级"),NATIONWIDE("RYJB-01","全国");
        private String code;
        private String name;

        honorLevel(String code,String name){
            this.code = code;
            this.name = name;
        }
        public static Map<String, String> codeMap = new HashMap<String, String>();

        static {
            for (int i = 0; i < values().length; i++) {
                codeMap.put(values()[i].code, values()[i].name);
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

    /**
     * 主要荣誉类型
     */
    public enum primaryHonor{
        LDMF("ZYRY-01","劳动模范"),WYJZ("ZYRY-02","五一奖章"),SBHQS("ZYRY-03","三八红旗手"),WSJZ("ZYRY-04","五四奖章")
        ,SHZYJSZ("ZYRY-05","优秀社会主义建设者"),GCSYJZ("ZYRY-06","光彩事业奖章"),GTLHGXJ("ZYRY-07","光彩事业国土绿化贡献奖")
        ,YXQY("ZYRY-08","优秀企业");
        private String code;
        private String name;

        primaryHonor(String code,String name){
            this.code = code;
            this.name = name;
        }
        public static Map<String, String> codeMap = new HashMap<String, String>();

        static {
            for (int i = 0; i < values().length; i++) {
                codeMap.put(values()[i].code, values()[i].name);
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


    /**
     * 证件照片类型
     */
    public enum documentType{
        PHOTO("ZJZPLX-01","照片"),IDENTITY("ZJZPLX-02","身份证"),DEGREE("ZJZPLX-03","学历学位证"),SHXYZ("ZJZPLX-04","社会信用证")
        ,STDJZ("ZJZPLX-05","社团登记证");
        private String code;
        private String name;

        documentType(String code,String name){
            this.code = code;
            this.name = name;
        }
        public static Map<String, String> codeMap = new HashMap<String, String>();

        static {
            for (int i = 0; i < values().length; i++) {
                codeMap.put(values()[i].code, values()[i].name);
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

    /**
     * 人员类型
     */
    public enum peopleType{
        ONE("RYLX-01","有制经济代表人士"),TWO("RYLX-02","非公有制经济代表人士");
        private String code;
        private String name;

        peopleType(String code,String name){
            this.code = code;
            this.name = name;
        }
        public static Map<String, String> codeMap = new HashMap<String, String>();

        static {
            for (int i = 0; i < values().length; i++) {
                codeMap.put(values()[i].code, values()[i].name);
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

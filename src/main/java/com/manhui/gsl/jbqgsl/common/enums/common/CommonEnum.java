package com.manhui.gsl.jbqgsl.common.enums.common;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 公共枚举
 */
public class CommonEnum {

    /**
     * 性别枚举
     */
    public enum ryxb{
        GIRL("XB-02","女"),MALE("XB-01","男");
        private String code;
        private String name;

        ryxb(String code,String name){
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
     * 是否枚举
     */
    public enum isNot{
        YES("SFYX-01","是"),NO("SFYX-02","否");
        private String code;
        private String name;

        isNot(String code,String name){
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
     * 婚姻状况
     */
    public enum marriage{
        MARRIED("HY—02","已婚"),UNMARRIED("HY—01","未婚"),DIVORCED("HY—07","离异");
        private String code;
        private String name;

        marriage(String code,String name){
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
     * 学历
     */
    public enum education{
        ZZ("XL-04","中专"),DZ("XL-03","大专"),BK("XL-02","本科"),SS("XL-01","硕士"),BS("XL-06","博士"),other("XL-05","其他");
        private String code;
        private String name;

        education(String code,String name){
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

    public enum zj_look{
        FRONT("1","正面"),OBVERSE("2","反面");
        private String code;
        private String name;

        zj_look(String code,String name){
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

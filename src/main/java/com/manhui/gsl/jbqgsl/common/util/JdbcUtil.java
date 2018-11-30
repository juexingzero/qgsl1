package com.manhui.gsl.jbqgsl.common.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class JdbcUtil {
    private final static String driver = "com.mysql.jdbc.Driver";
    private final static String url    = "jdbc:mysql://192.168.0.13:3306/jbqgsl_dev?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true";
    private final static String user   = "root";
    private final static String pwd    = "Abc@123456";

    public static void main( String[] args ) {
        String tableName = "member_qy_qyfrxx";
        List<TableField> fieldList = getTableInfo( tableName );
        if ( !fieldList.isEmpty() ) {
            StringBuilder builderTable = new StringBuilder();
            StringBuilder builderEntity = new StringBuilder();
            for ( TableField field : fieldList ) {
                createEntity( field );
                builderTable.append( field.getName() + "," );
                builderEntity.append( "#{" + strOneLower( field.getName() ) + "}," );
            }
            createInsertSql( builderTable.toString(), builderEntity.toString(), tableName );
            createUpdateSql( fieldList, tableName );
            createResultMap( fieldList );
        }
    }

    /**
     * 创建 实体
     *
     * @param field
     */
    public static void createEntity( TableField field ) {
        //System.out.println( "//" + field.getRemarks() );
        System.out.println( "private " + field.getType() + " " + strOneLower( field.getName().toLowerCase() ) + ";         //" + field.getRemarks() );
    }

    /**
     * 获得新增sql
     *
     */
    public static void createInsertSql( String tableField, String entityField, String tableName ) {
        entityField = entityField.substring( 0, entityField.length() - 1 ).toLowerCase();
        tableField = tableField.substring( 0, tableField.length() - 1 ).toUpperCase();
        System.out.println( "insert into " + tableName + "(" + tableField + ") values (" + entityField + ")" );
    }

    /**
     * 获得 修改语句sql
     */
    public static void createUpdateSql( List<TableField> fieldList, String tableName ) {
        System.out.println( "update " + tableName + "\n" + "<set>" );
        String ifstr = "";
        for ( TableField field : fieldList ) {
            if ( field.getType().equals( "Integer" ) ) {
                ifstr = "<if test=\"" + strOneLower( field.getName() ) + "!=null\">";
            }
            else if ( field.getType().equals( "String" ) ) {
                ifstr = "<if test=\"" +
                        strOneLower( field.getName().toLowerCase() ) +
                        "!=null and " +
                        strOneLower( field.getName().toLowerCase() ) +
                        " !=\'\' \">";
            }
            System.out.println( ifstr + field.getName() + "=#{" + strOneLower( field.getName().toLowerCase() ) + "}," + "</if>" );
        }
        System.out.println( "</set>" );
    }

    /**
     * 创建 ResultMap
     *
     * @param fieldList
     */
    public static void createResultMap( List<TableField> fieldList ) {
        System.out.println( "<resultMap id=\"BaseResultMap\" type=\"\">" );
        String type = convertType( fieldList.get( 0 ).getType() );
        System.out.println(
                "<id column=\"" +
                        fieldList.get( 0 ).getName() +
                        "\" jdbcType=\"" +
                        type +
                        "\" property=\"" +
                        strOneLower( fieldList.get( 0 ).getName().toLowerCase() ) +
                        "\" />" );
        TableField field;
        for ( int i = 1; i < fieldList.size(); i++ ) {
            field = fieldList.get( i );
            System.out.println(
                    "<result column=\"" +
                            field.getName() +
                            "\" jdbcType=\"" +
                            convertType( field.getType() ) +
                            "\" property=\"" +
                            strOneLower( field.getName().toLowerCase() ) +
                            "\" />" );
        }
        System.out.println( "</resultMap>" );
    }

    /**
     * 转换类型
     *
     * @param type
     * @return
     */
    public static String convertType( String type ) {
        if ( type.equals( "Integer" ) ) {
            type = "INTEGER";
        }
        else if ( type.equals( "String" ) ) {
            type = "VARCHAR";
        }
        return type;
    }

    /**
     * 获得指定表所有字段
     *
     * @param tableName
     * @return
     */
    public static List<TableField> getTableInfo( String tableName ) {
        List<TableField> fieldList = new ArrayList<TableField>();
        Connection conn = null;
        DatabaseMetaData dbmd = null;
        try {
            conn = getConnections();
            dbmd = conn.getMetaData();
            ResultSet colRet = dbmd.getColumns( null, "%", tableName, "%" );
            String columnName;
            String columnType;
            String remarks;
            TableField tableField;
            while ( colRet.next() ) {
                columnName = colRet.getString( "COLUMN_NAME" );
                columnType = colRet.getString( "TYPE_NAME" );
                remarks = colRet.getString( "REMARKS" );
                //System.out.println(columnName + " " + columnType + " " + remarks );
                tableField = new TableField();
                tableField.setName( columnName );
                tableField.setType( columnType );
                tableField.setRemarks( remarks );
                fieldList.add( tableField );
            }
        }
        catch ( SQLException e ) {
            e.printStackTrace();
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
        finally {
            try {
                conn.close();
            }
            catch ( SQLException e ) {
                e.printStackTrace();
            }
        }
        return fieldList;
    }

    /**
     * 字符串首字母 转小写
     *
     * @param str
     * @return
     */
    public static String strOneLower( String str ) {
        char s = Character.toLowerCase( str.charAt( 0 ) );
        str = new StringBuilder().append( s ) + str.substring( 1, str.length() );
        return str;
    }

    /**
     * 链接数据库
     *
     * @return
     * @throws Exception
     */
    private static Connection getConnections() throws Exception {
        Connection conn = null;
        try {
            Properties props = new Properties();
            props.put( "remarksReporting", "true" );
            props.put( "user", user );
            props.put( "password", pwd );
            Class.forName( driver );
            conn = DriverManager.getConnection( url, props );
        }
        catch ( Exception e ) {
            e.printStackTrace();
            throw e;
        }
        return conn;
    }

    public static class TableField {
        private String name;
        private String type;
        private String remarks;

        public String getName() {
            return name;
        }

        public void setName( String name ) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType( String type ) {
            if ( type.equals( "INT" ) ) {
                type = "Integer";
            }
            else if ( type.equals( "VARCHAR" ) ) {
                type = "String";
            }
            this.type = type;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks( String remarks ) {
            this.remarks = remarks;
        }
    }
}

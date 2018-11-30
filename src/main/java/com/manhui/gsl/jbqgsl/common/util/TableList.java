package com.manhui.gsl.jbqgsl.common.util;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import org.springframework.util.StringUtils;
import lombok.Data;

@Data
public class TableList<T> implements Serializable {
    private String  str;      // 筛选条件
    private String  dateStr;  // 时间条件
    private String  dateType; // 时间类型
    private String  dateStart;// 开始时间
    private String  dateEnd;  // 结束时间
    private String  orderBy;  // 排序
    private Integer pageNo;   // 开始页
    private Integer pageSize; // 分页数
    private String  sqlWhere; // 数据条件
    private String  mode;     // 模式

    /**
     * 数据转化为SQL 时间格式化
     * 
     * @return TableList
     */
    public PageModel<T> initData( TableList tableList ) {
        SimpleDateFormat sf = new SimpleDateFormat( "yyyy-MM-dd" );
        Calendar c = Calendar.getInstance();
        Date date = new Date();
        if ( this.getDateStr() != null ) {
            switch ( tableList.getDateStr() ) {
                case "今天" :
                    tableList.setDateStart( sf.format( date ) );
                    tableList.setDateEnd( sf.format( date ) );
                    break;
                case "最近一周" :
                    tableList.setDateEnd( sf.format( date ) );
                    c.setTime( date );
                    c.add( Calendar.DAY_OF_MONTH, -7 );
                    tableList.setDateStart( sf.format( c.getTime() ) );
                    break;
                case "最近一月" :
                    tableList.setDateEnd( sf.format( date ) );
                    c.setTime( date );
                    c.add( Calendar.MONTH, -1 );
                    tableList.setDateStart( sf.format( c.getTime() ) );
                    break;
                default :
                    break;
            }
        }
        List<String> sqlWhereList = new ArrayList<String>();
        if ( tableList.getStr() != null && !tableList.getStr().equals( "" ) ) {
            String[] split = tableList.getStr().split( "," );
            for ( int i = 0; i < split.length; i++ ) {
                if ( split[i].split( "::" ).length == 1 ) {
                    continue;
                }
                String[] split2 = split[i].split( "::" );
                String where = "";
                String wString = "";
                if ( isValid( split2[1] ) ) {
                    String strWhere = "";
                    strWhere = split2[1];
                    if ( strWhere.contains( "#" ) ) {
                        where = "('" + strWhere.replace( "#", "','" ) + "')";
                        wString = "IN";
                    }
                    else {
                        if ( Integer.parseInt( split2[2] ) == 0 ) {
                            where = "\'%" + strWhere + "%\'";
                            wString = "like";
                        }
                        else {
                            where = strWhere;
                            wString = "=";
                        }
                    }
                }
                else {
                    where = "";
                }
                if ( !where.equals( "" ) ) {
                    Boolean bool = true;
                    String[] split3 = split2[0].split( "-" );
                    for ( int j = 0; j < sqlWhereList.size(); j++ ) {
                        String str1 = sqlWhereList.get( j );
                        if ( sqlWhereList.get( j ).indexOf( split3[0] ) > -1 ) {
                            str1 = sqlWhereList.get( j ).substring( 0, sqlWhereList.get( j ).length() - 1 );
                            String str2 = "";
                            for ( int k = 0; k < split3.length; k++ ) {
                                str2 += " or " + split3[k] + " " + wString + " " + where + "";
                            }
                            str1 += str2 + ")";
                            sqlWhereList.set( j, str1 );
                            bool = false;
                            break;
                        }
                    }
                    if ( bool ) {
                        if ( split3.length > 1 ) {
                            String str1 = " and ( ";
                            for ( int k = 0; k < split3.length; k++ ) {
                                if ( k == 0 ) {
                                    str1 += "" + split3[k] + " " + wString + " " + where + "";
                                }
                                else {
                                    str1 += " or " + split3[k] + " " + wString + " " + where + "";
                                }
                            }
                            sqlWhereList.add( str1 + ")" );
                        }
                        else {
                            sqlWhereList.add( " and (" + split2[0] + " " + wString + " " + where + ")" );
                        }
                    }
                }
            }
        }
        String sqlWhere = "";
        for ( String str1 : sqlWhereList ) {
            sqlWhere += str1;
        }
        this.sqlWhere = sqlWhere;
        PageModel<T> pageModel = new PageModel<>();
        try {
            if ( tableList.getDateStart() != null && !tableList.getDateStart().equals( "" ) ) {
                pageModel.setDateStart( sf.parse( tableList.getDateStart() ) );
            }
            if ( tableList.getDateEnd() != null && !tableList.getDateEnd().equals( "" ) ) {
                pageModel.setDateEnd( sf.parse( tableList.getDateEnd() ) );
            }
            pageModel.setSqlWhere( sqlWhere );
            pageModel.setPageNo( tableList.getPageNo() );
            pageModel.setDateTitle( tableList.getDateType() );
            int pageSize = tableList.getPageSize();
            pageModel.setPageSize( pageSize );
        }
        catch ( Exception e ) {}
        return pageModel;
    }

    /**
     * 数据转化为SQL 时间格式化
     * 
     * @return
     */
    public void initData() {
        SimpleDateFormat sf = new SimpleDateFormat( "yyyy-MM-dd" );
        Calendar c = Calendar.getInstance();
        Date date = new Date();
        if ( this.getDateStr() != null ) {
            switch ( dateStr ) {
                case "今天" :
                    this.dateStart = sf.format( date );
                    this.dateEnd = sf.format( date );
                    break;
                case "最近一周" :
                    this.dateEnd = sf.format( date );
                    c.setTime( date );
                    c.add( Calendar.DAY_OF_MONTH, -7 );
                    this.dateStart = sf.format( c.getTime() );
                    break;
                case "最近一月" :
                    this.dateEnd = sf.format( date );
                    c.setTime( date );
                    c.add( Calendar.MONTH, -1 );
                    this.dateStart = sf.format( c.getTime() );
                    break;
                default :
                    break;
            }
        }
        List<String> sqlWhereList = new ArrayList<String>();
        if ( !StringUtils.isEmpty( this.str ) ) {
            String[] split = this.str.split( "," );
            for ( int i = 0; i < split.length; i++ ) {
                if ( split[i].split( "::" ).length == 1 ) {
                    continue;
                }
                String[] split2 = split[i].split( "::" );
                String where = "";
                String wString = "";
                if ( isValid( split2[1] ) ) {
                    String strWhere = "";
                    strWhere = split2[1];
                    if ( Integer.parseInt( split2[2] ) == 0 ) {
                        where = "\'%" + strWhere + "%\'";
                        wString = "like";
                    }
                    else {
                        where = strWhere;
                        wString = "=";
                    }
                }
                else {
                    where = "";
                }
                if ( !where.equals( "" ) ) {
                    Boolean bool = true;
                    String[] split3 = split2[0].split( "-" );
                    for ( int j = 0; j < sqlWhereList.size(); j++ ) {
                        String str1 = sqlWhereList.get( j );
                        if ( sqlWhereList.get( j ).indexOf( split3[0] ) > -1 ) {
                            str1 = sqlWhereList.get( j ).substring( 0, sqlWhereList.get( j ).length() - 1 );
                            String str2 = "";
                            for ( int k = 0; k < split3.length; k++ ) {
                                str2 += " or " + split3[k] + " " + wString + " " + where + "";
                            }
                            str1 += str2 + ")";
                            sqlWhereList.set( j, str1 );
                            bool = false;
                            break;
                        }
                    }
                    if ( bool ) {
                        if ( split3.length > 1 ) {
                            String str1 = " and ( ";
                            for ( int k = 0; k < split3.length; k++ ) {
                                if ( k == 0 ) {
                                    str1 += "" + split3[k] + " " + wString + " " + where + "";
                                }
                                else {
                                    str1 += " or " + split3[k] + " " + wString + " " + where + "";
                                }
                            }
                            sqlWhereList.add( str1 + ")" );
                        }
                        else {
                            sqlWhereList.add( " and (" + split2[0] + " " + wString + " " + where + ")" );
                        }
                    }
                }
            }
        }
        String sqlWhere = "";
        for ( String str1 : sqlWhereList ) {
            sqlWhere += str1;
        }
        this.sqlWhere = sqlWhere;
    }

    public String getMode() {
        return mode;
    }

    public void setMode( String mode ) {
        this.mode = mode;
    }

    /**
     * 参数校验:过滤特殊sql防止SQL注入
     *
     * @param str
     * @return
     */
    public static Boolean isValid( String str ) {
        String reg = "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|" +
                "(\\b(select|update|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute)\\b)";
        Pattern sqlPattern = Pattern.compile( reg, Pattern.CASE_INSENSITIVE );
        return !sqlPattern.matcher( str ).find();
    }
}

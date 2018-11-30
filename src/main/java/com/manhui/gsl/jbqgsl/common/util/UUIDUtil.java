package com.manhui.gsl.jbqgsl.common.util;

import java.util.UUID;

public class UUIDUtil {
    public static String getUUID() {
        return UUID.randomUUID().toString().replace( "-", "" );
    }

    public static void main( String[] args ) {
        System.out.print( getUUID() );
    }
}

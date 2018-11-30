package com.manhui.gsl.jbqgsl.common;

import lombok.Data;

@Data
public class ResultMessage {
    private Integer state;
    private String  message;
    private Object  data;

    public ResultMessage( String message ) {
        this.state = 1;
        this.message = message;
    }

    public ResultMessage() {
        this.state = 0;
        this.message = "成功";
    }

    public ResultMessage( Object data ) {
        this.state = 0;
        this.message = "成功";
        this.data = data;
    }
}

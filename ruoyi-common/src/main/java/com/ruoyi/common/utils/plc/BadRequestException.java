package com.ruoyi.common.utils.plc;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
@Getter
public class BadRequestException extends RuntimeException {
    private Integer status = INTERNAL_SERVER_ERROR.value();

    public BadRequestException(String msg){
        super(msg);
    }

    public BadRequestException(HttpStatus status,String msg){
        super(msg);
        this.status = status.value();
    }

    public BadRequestException(Integer code,String msg){
        super(msg);
        this.status = code;
    }
}

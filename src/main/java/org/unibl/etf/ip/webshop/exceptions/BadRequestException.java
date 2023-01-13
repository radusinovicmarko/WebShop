package org.unibl.etf.ip.webshop.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
public class BadRequestException extends HttpException {
    public BadRequestException() {
        super(HttpStatus.BAD_REQUEST, null);
    }
    public BadRequestException(Object data) {
        super(HttpStatus.BAD_REQUEST, data);
    }
}

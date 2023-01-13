package org.unibl.etf.ip.webshop.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
public class ForbiddenException extends HttpException {
    public ForbiddenException() {
        super(HttpStatus.FORBIDDEN, null);
    }
    public ForbiddenException(Object data) {
        super(HttpStatus.FORBIDDEN, data);
    }
}

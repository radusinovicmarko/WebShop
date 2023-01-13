package org.unibl.etf.ip.webshop.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
public class ConflictException extends HttpException {
    public ConflictException() {
        super(HttpStatus.CONFLICT, null);
    }

    public ConflictException(Object data) {
        super(HttpStatus.CONFLICT, data);
    }
}

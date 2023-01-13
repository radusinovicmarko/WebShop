package org.unibl.etf.ip.webshop.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
public class NotFoundException extends HttpException {
    public NotFoundException() {
        super(HttpStatus.NOT_FOUND, null);
    }
    public NotFoundException(Object data) {
        super(HttpStatus.NOT_FOUND, data);
    }
}

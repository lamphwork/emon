package lamph.emon.common.exceptions;

import java.util.Map;

public class BusinessException extends RuntimeException {

    protected Map<String, Object> details;

    public BusinessException(String errorCode) {
        this(errorCode, null);
    }

    public BusinessException(String errorCode, Map<String, Object> details) {
        super(errorCode);
        this.details = details;
    }
}

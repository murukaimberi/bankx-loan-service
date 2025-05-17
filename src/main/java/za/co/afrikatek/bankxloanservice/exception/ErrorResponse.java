/**
 * Author : murukaigumbo-mberi
 * Date   : 2025/05/17
 * Time   : 16:31
 */

package za.co.afrikatek.bankxloanservice.exception;

import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ErrorResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private String message;
    private HttpStatus status;
    private String entity;
    private Map<String, List<String>> errors = new HashMap<>();

    public ErrorResponse(String message, HttpStatus status, String entity, Map<String, List<String>> errors) {
        this.message = message;
        this.status = status;
        this.entity = entity;
        this.errors = errors;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getEntity() {
        return entity;
    }

    public Map<String, List<String>> getErrors() {
        return errors;
    }
}

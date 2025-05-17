/**
 * Author : murukaigumbo-mberi
 * Date   : 2025/05/17
 * Time   : 16:43
 */

package za.co.afrikatek.bankxloanservice.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Status400BadRequestException extends RuntimeException {
    private String entity;
    private Map<String, List<String>> errors = new HashMap<>();

    public Status400BadRequestException(String message, String entity, Map<String, List<String>> errors) {
        super(message);
        this.entity = entity;
        this.errors = errors;
    }

    public String getEntity() {
        return entity;
    }
    public Map<String, List<String>> getErrors() {
        return errors;
    }
}

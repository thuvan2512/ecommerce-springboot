package com.thunv.ecommerceou.res;

import lombok.Getter;
import lombok.Setter;

public class ModelResponse {
    @Getter
    @Setter
    private String code;
    @Getter
    @Setter
    private String message;
    @Getter
    @Setter
    private Object data;
    public ModelResponse() {
    }
    public ModelResponse(String code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

}

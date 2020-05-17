package com.fabelio.test.model;

import org.hibernate.validator.constraints.NotBlank;

public class SearchCriteria {

    @NotBlank(message = "URL can't be empty!")
    private String uri;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
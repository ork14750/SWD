package com.example.swd;

import java.util.List;

public class RestSWResponse {
    private Integer count;
    private String next;
    private List<Planets> results;

    public Integer getCount() {
        return count;
    }

    public String getNext() {
        return next;
    }

    public List<Planets> getResults() {
        return results;
    }
}

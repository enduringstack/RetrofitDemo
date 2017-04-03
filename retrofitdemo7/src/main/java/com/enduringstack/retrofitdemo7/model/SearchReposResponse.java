package com.enduringstack.retrofitdemo7.model;

/**
 * Created by sebastianmazur on 19.12.15.
 */
public class SearchReposResponse {
    private int total_count;
    private boolean incomplete_results;
    private Repository[] items;

    public SearchReposResponse() {

    }

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public boolean isIncomplete_results() {
        return incomplete_results;
    }

    public void setIncomplete_results(boolean incomplete_results) {
        this.incomplete_results = incomplete_results;
    }

    public Repository[] getItems() {
        return items;
    }

    public void setItems(Repository[] items) {
        this.items = items;
    }
}
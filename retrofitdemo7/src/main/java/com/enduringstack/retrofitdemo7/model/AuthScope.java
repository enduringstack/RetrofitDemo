package com.enduringstack.retrofitdemo7.model;

/**
 * Created by sebastianmazur on 18.12.15.
 */
public class AuthScope {
    private String[] scopes;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String[] getScopes() {
        return scopes;
    }

    public void setScopes(String[] scopes) {
        this.scopes = scopes;
    }

    private String note;

    public AuthScope() {
    }

    public AuthScope(String[] scopes, String note) {
        this.scopes = scopes;
        this.note = note;
    }
}

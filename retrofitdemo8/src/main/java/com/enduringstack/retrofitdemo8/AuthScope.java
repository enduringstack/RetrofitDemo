package com.enduringstack.retrofitdemo8;

/**
 * Created by chenfuduo on 17-4-1.
 */

public class AuthScope {

    //A list of scopes that this authorization is in.
    private String[] scopes;

    //A note to remind you what the OAuth token is for. Tokens not associated with a specific OAuth application (i.e. personal access tokens) must have a unique note.
    private String note;

    public AuthScope(String[] scopes, String note) {
        this.scopes = scopes;
        this.note = note;
    }

    public String[] getScopes() {
        return scopes;
    }

    public void setScopes(String[] scopes) {
        this.scopes = scopes;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}

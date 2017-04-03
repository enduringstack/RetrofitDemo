package com.enduringstack.retrofitdemo7.model;

/**
 * Created by sebastianmazur on 19.12.15.
 */
public class Subscription {
    private boolean subscribed;
    private boolean ignored;

    public Subscription() {
    }

    public boolean isSubscribed() {
        return subscribed;
    }

    public Subscription setSubscribed(boolean subscribed) {
        this.subscribed = subscribed;
        return this;
    }

    public boolean isIgnored() {
        return ignored;
    }

    public Subscription setIgnored(boolean ignored) {
        this.ignored = ignored;
        return this;
    }
}

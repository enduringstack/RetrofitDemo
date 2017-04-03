package com.enduringstack.retrofitdemo7.model;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by sebastianmazur on 18.12.15.
 */
public class Repository {
    private long id;
    private String name;
    private String full_name;
    private String description;
    private String html_url;
    private String url;
    private String language;
    private int forks_count;
    private int stargazers_count;
    private int watchers_count;
    private Date created_at;
    private Date updated_at;

    private boolean starred;
    private boolean watched;

    private Owner owner;

    public Repository() {
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getForks_count() {
        return forks_count;
    }

    public void setForks_count(int forks_count) {
        this.forks_count = forks_count;
    }

    public int getStargazers_count() {
        return stargazers_count;
    }

    public void setStargazers_count(int stargazers_count) {
        this.stargazers_count = stargazers_count;
    }

    public int getWatchers_count() {
        return watchers_count;
    }

    public void setWatchers_count(int watchers_count) {
        this.watchers_count = watchers_count;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public boolean isStarred() {
        return starred;
    }

    public void setStarred(boolean starred) {
        this.starred = starred;
    }

    public boolean isWatched() {
        return watched;
    }

    public void setWatched(boolean watched) {
        this.watched = watched;
    }

    public static class Owner {
        private String login;
        private long id;
        private String avatar_url;
        private String gravatar_id;
        private String url;
        private String html_url;
        private String followers_url;
        private String following_url;
        private String gists_url;
        private String starred_url;
        private String subscriptions_url;
        private String organizations_url;
        private String repos_url;
        private String events_url;
        private String received_events_url;
        private String type;
        private String site_admin;

        public Owner() {
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getAvatar_url() {
            return avatar_url;
        }

        public void setAvatar_url(String avatar_url) {
            this.avatar_url = avatar_url;
        }

        public String getGravatar_id() {
            return gravatar_id;
        }

        public void setGravatar_id(String gravatar_id) {
            this.gravatar_id = gravatar_id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getHtml_url() {
            return html_url;
        }

        public void setHtml_url(String html_url) {
            this.html_url = html_url;
        }

        public String getFollowers_url() {
            return followers_url;
        }

        public void setFollowers_url(String followers_url) {
            this.followers_url = followers_url;
        }

        public String getFollowing_url() {
            return following_url;
        }

        public void setFollowing_url(String following_url) {
            this.following_url = following_url;
        }

        public String getGists_url() {
            return gists_url;
        }

        public void setGists_url(String gists_url) {
            this.gists_url = gists_url;
        }

        public String getStarred_url() {
            return starred_url;
        }

        public void setStarred_url(String starred_url) {
            this.starred_url = starred_url;
        }

        public String getSubscriptions_url() {
            return subscriptions_url;
        }

        public void setSubscriptions_url(String subscriptions_url) {
            this.subscriptions_url = subscriptions_url;
        }

        public String getOrganizations_url() {
            return organizations_url;
        }

        public void setOrganizations_url(String organizations_url) {
            this.organizations_url = organizations_url;
        }

        public String getRepos_url() {
            return repos_url;
        }

        public void setRepos_url(String repos_url) {
            this.repos_url = repos_url;
        }

        public String getEvents_url() {
            return events_url;
        }

        public void setEvents_url(String events_url) {
            this.events_url = events_url;
        }

        public String getReceived_events_url() {
            return received_events_url;
        }

        public void setReceived_events_url(String received_events_url) {
            this.received_events_url = received_events_url;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSite_admin() {
            return site_admin;
        }

        public void setSite_admin(String site_admin) {
            this.site_admin = site_admin;
        }
    }

    /////////////////////

    public static final String TABLE_NAME = "repositories";

    public static String getCreateTableSql() {
        String query = "" +
                "CREATE TABLE repositories (" +
                "   id              INTEGER PRIMARY KEY," +
                "   description     TEXT," +
                "   name            TEXT," +
                "   owner_name      TEXT," +
                "   starred         INTEGER DEFAULT 0," +
                "   watched         INTEGER DEFAULT 0" +
                ");";

        return query;
    }

    public static List<Repository> convertCursorToModelList(Cursor cursor) {
        List<Repository> list = new ArrayList<Repository>();

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    Repository repo = new Repository();
                    repo.setId(cursor.getLong(cursor.getColumnIndex("id")));
                    repo.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                    repo.setName(cursor.getString(cursor.getColumnIndex("name")));
                    Owner owner = new Owner();
                    owner.setLogin(cursor.getString(cursor.getColumnIndex("owner_name")));
                    repo.setOwner(owner);
                    int starred = cursor.getInt(cursor.getColumnIndex("starred"));
                    repo.setStarred(starred == 1);
                    int watched = cursor.getInt(cursor.getColumnIndex("watched"));
                    repo.setWatched(watched == 1);

                    list.add(repo);
                    cursor.moveToNext();
                }
            }

            cursor.close();
        }

        return list;
    }

    public static ContentValues convertModelToInsert(Repository repo) {
        ContentValues values = new ContentValues();
        values.put("id", repo.getId());
        values.put("description", repo.getDescription());
        values.put("name", repo.getName());
        values.put("owner_name", repo.getOwner().getLogin());

        return values;
    }
}

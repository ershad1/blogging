package com.simple.blogging.model.post;

import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

@Projection(types = {Post.class})
public interface FlatPost {
    Long getId();

    String getContent();

    Boolean getIsActive();

    Date getPostedDate();

    AppUser getAppUser();

    interface AppUser {
        String getUsername();

        String getFullName();
    }
}

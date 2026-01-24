package com.zimpy.review.dto;

import java.time.LocalDateTime;

public class ReviewResponse {
    private Long id;
    private int rating;
    private String comment;
    private String userName;
    private LocalDateTime createAt;

    public ReviewResponse(Long id, int rating, String comment, String userName, LocalDateTime createAt) {
        this.id = id;
        this.rating = rating;
        this.comment = comment;
        this.userName = userName;
        this.createAt = createAt;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

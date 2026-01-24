package com.zimpy.banner.dto;

import com.zimpy.banner.entity.BannerPlacement;
import com.zimpy.banner.entity.BannerStatus;
import jakarta.validation.constraints.Min;

import java.time.LocalDateTime;

public class BannerUpdateRequest {

    private String title;
    private String link;
    private BannerPlacement placement;
    private BannerStatus status;

    @Min(value = 1, message = "Priority must be at least 1")
    private Integer priority;

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String showDays;

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public BannerPlacement getPlacement() {
        return placement;
    }

    public void setPlacement(BannerPlacement placement) {
        this.placement = placement;
    }

    public BannerStatus getStatus() {
        return status;
    }

    public void setStatus(BannerStatus status) {
        this.status = status;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getShowDays() {
        return showDays;
    }

    public void setShowDays(String showDays) {
        this.showDays = showDays;
    }
}

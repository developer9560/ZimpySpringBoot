package com.zimpy.banner.dto;

import com.zimpy.banner.entity.BannerPlacement;
import com.zimpy.banner.entity.BannerStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class BannerCreateRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Link is required")
    private String link;

    @NotNull(message = "Placement is required")
    private BannerPlacement placement;

    private BannerStatus status = BannerStatus.INACTIVE;

    @Min(value = 1, message = "Priority must be at least 1")
    private int priority = 1;

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String showDays; // CSV: "MON,WED,FRI"

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

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
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

package com.zimpy.banner.dto;

import com.zimpy.banner.entity.BannerPlacement;

import java.time.LocalDateTime;

public class PublicBannerResponse {

    private Long id;
    private String mobileUrl;
    private String tabletUrl;
    private String desktopUrl;
    private String title;
    private String link;
    private BannerPlacement placement;
    private int priority;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String showDays;

    // Constructor
    public PublicBannerResponse() {
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMobileUrl() {
        return mobileUrl;
    }

    public void setMobileUrl(String mobileUrl) {
        this.mobileUrl = mobileUrl;
    }

    public String getTabletUrl() {
        return tabletUrl;
    }

    public void setTabletUrl(String tabletUrl) {
        this.tabletUrl = tabletUrl;
    }

    public String getDesktopUrl() {
        return desktopUrl;
    }

    public void setDesktopUrl(String desktopUrl) {
        this.desktopUrl = desktopUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
}

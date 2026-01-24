package com.zimpy.banner.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
@Entity
@Table(
        name = "banners",
        indexes = {
                @Index(name = "idx_banner_status", columnList = "status"),
                @Index(name = "idx_banner_placement", columnList = "placement"),
                @Index(name = "idx_banner_priority", columnList = "priority")
        }
)
public class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Device-specific images
    @Column(name = "mobile_url")
    private String mobileUrl;

    @Column(name = "tablet_url")
    private String tabletUrl;

    @Column(name = "desktop_url")
    private String desktopUrl;

    @Column(name = "show_days")
    private String showDays; // example value :"MON,Wed, fri"



    // Banner content
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String link;

    // WHERE banner is shown
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BannerPlacement placement;

    // ACTIVE / INACTIVE / PAUSED
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BannerStatus status = BannerStatus.INACTIVE;

    // Ordering
    private int priority;

    // Analytics
    @Column(name = "total_clicked", nullable = false)
    private long totalClicked = 0;

    // Scheduling
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    // Audit
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @PrePersist
    void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // getters & setters

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

    public String getDesktopUrl() {
        return desktopUrl;
    }

    public void setDesktopUrl(String desktopUrl) {
        this.desktopUrl = desktopUrl;
    }

    public String getTabletUrl() {
        return tabletUrl;
    }

    public void setTabletUrl(String tabletUrl) {
        this.tabletUrl = tabletUrl;
    }


    public String getShowDays() {
        return showDays;
    }

    public void setShowDays(String showDays) {
        this.showDays = showDays;
    }

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

    public long getTotalClicked() {
        return totalClicked;
    }

    public void setTotalClicked(long totalClicked) {
        this.totalClicked = totalClicked;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
}

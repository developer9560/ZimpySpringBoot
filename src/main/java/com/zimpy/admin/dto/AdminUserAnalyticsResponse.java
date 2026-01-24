package com.zimpy.admin.dto;

public class AdminUserAnalyticsResponse {
    private long totalUsers;
    private long activeUsers;
    private long inActiveUsers;
    private long blockeUsers;
    private long newToday;
    private long newThisMonth;

    public AdminUserAnalyticsResponse(long totalUsers, long activeUsers, long blockeUsers, long inActiveUsers ,long newToday, long newThisMonth) {
        this.totalUsers = totalUsers;
        this.activeUsers = activeUsers;
        this.blockeUsers = blockeUsers;
        this.newToday = newToday;
        this.inActiveUsers = inActiveUsers;
        this.newThisMonth = newThisMonth;
    }

    public long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public long getActiveUsers() {
        return activeUsers;
    }

    public void setActiveUsers(long activeUsers) {
        this.activeUsers = activeUsers;
    }

    public long getBlockeUsers() {
        return blockeUsers;
    }

    public long getInActiveUsers() {
        return inActiveUsers;
    }

    public void setInActiveUsers(long inActiveUsers) {
        this.inActiveUsers = inActiveUsers;
    }

    public void setBlockeUsers(long blockeUsers) {
        this.blockeUsers = blockeUsers;
    }

    public long getNewToday() {
        return newToday;
    }

    public void setNewToday(long newToday) {
        this.newToday = newToday;
    }

    public long getNewThisMonth() {
        return newThisMonth;
    }

    public void setNewThisMonth(long newThisMonth) {
        this.newThisMonth = newThisMonth;
    }
}

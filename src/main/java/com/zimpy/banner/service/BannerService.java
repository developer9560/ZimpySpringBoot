package com.zimpy.banner.service;

import com.zimpy.banner.dto.BannerAdminResponse;
import com.zimpy.banner.dto.BannerCreateRequest;
import com.zimpy.banner.dto.BannerUpdateRequest;
import com.zimpy.banner.dto.PublicBannerResponse;
import com.zimpy.banner.entity.Banner;
import com.zimpy.banner.entity.BannerPlacement;
import com.zimpy.banner.entity.BannerStatus;
import com.zimpy.banner.repository.BannerRepository;
import com.zimpy.exception.ResourceNotFoundException;
import com.zimpy.util.ImageUploadService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class BannerService {

    @Autowired
    private BannerRepository bannerRepository;

    @Autowired
    private ImageUploadService imageUploadService;

    // ============ ADMIN OPERATIONS ============

    /**
     * Create a new banner
     * Handles image uploads for mobile, tablet, and desktop
     */
    @Transactional
    public BannerAdminResponse createBanner(
            BannerCreateRequest request,
            MultipartFile mobileImage,
            MultipartFile tabletImage,
            MultipartFile desktopImage) {
        Banner banner = new Banner();

        // Upload images and get URLs
        if (mobileImage != null && !mobileImage.isEmpty()) {
            String mobileUrl = imageUploadService.uploadImage(mobileImage);
            banner.setMobileUrl(mobileUrl);
        }

        if (tabletImage != null && !tabletImage.isEmpty()) {
            String tabletUrl = imageUploadService.uploadImage(tabletImage);
            banner.setTabletUrl(tabletUrl);
        }

        if (desktopImage != null && !desktopImage.isEmpty()) {
            String desktopUrl = imageUploadService.uploadImage(desktopImage);
            banner.setDesktopUrl(desktopUrl);
        }

        // Set other fields
        banner.setTitle(request.getTitle());
        banner.setLink(request.getLink());
        banner.setPlacement(request.getPlacement());
        banner.setStatus(request.getStatus() != null ? request.getStatus() : BannerStatus.INACTIVE);
        banner.setPriority(request.getPriority());
        banner.setStartDate(request.getStartDate());
        banner.setEndDate(request.getEndDate());
        banner.setShowDays(request.getShowDays());

        Banner saved = bannerRepository.save(banner);
        return mapToAdminResponse(saved);
    }

    /**
     * Get all banners for admin (with pagination)
     */
    public Page<BannerAdminResponse> getAllBanners(Pageable pageable) {
        return bannerRepository.findAllActive(pageable)
                .map(this::mapToAdminResponse);
    }

    /**
     * Get banners filtered by status
     */
    public Page<BannerAdminResponse> getBannersByStatus(BannerStatus status, Pageable pageable) {
        return bannerRepository.findByStatusAndDeletedAtIsNull(status, pageable)
                .map(this::mapToAdminResponse);
    }

    /**
     * Get banners filtered by placement
     */
    public Page<BannerAdminResponse> getBannersByPlacement(BannerPlacement placement, Pageable pageable) {
        return bannerRepository.findByPlacementAndDeletedAtIsNull(placement, pageable)
                .map(this::mapToAdminResponse);
    }

    /**
     * Get single banner by ID
     */
    public BannerAdminResponse getBannerById(Long id) {
        Banner banner = bannerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Banner not found with id: " + id));
        return mapToAdminResponse(banner);
    }

    /**
     * Update banner metadata (without images)
     */
    @Transactional
    public BannerAdminResponse updateBanner(Long id, BannerUpdateRequest request) {
        Banner banner = bannerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Banner not found with id: " + id));

        // Only update fields that are actually provided (not null)
        if (request.getTitle() != null && !request.getTitle().isEmpty())
            banner.setTitle(request.getTitle());
        if (request.getLink() != null && !request.getLink().isEmpty())
            banner.setLink(request.getLink());
        if (request.getPlacement() != null)
            banner.setPlacement(request.getPlacement());
        if (request.getStatus() != null)
            banner.setStatus(request.getStatus());
        if (request.getPriority() != null)
            banner.setPriority(request.getPriority());

        if (request.getStartDate() != null)
            banner.setStartDate(request.getStartDate());
        if (request.getEndDate() != null)
            banner.setEndDate(request.getEndDate());
        if (request.getShowDays() != null)
            banner.setShowDays(request.getShowDays());

        Banner updated = bannerRepository.save(banner);
        return mapToAdminResponse(updated);
    }

    /**
     * Update banner image for specific device
     */
    @Transactional
    public BannerAdminResponse updateBannerImage(Long id, String deviceType, MultipartFile image) {
        Banner banner = bannerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Banner not found with id: " + id));

        String imageUrl = imageUploadService.uploadImage(image);

        switch (deviceType.toLowerCase()) {
            case "mobile" -> banner.setMobileUrl(imageUrl);
            case "tablet" -> banner.setTabletUrl(imageUrl);
            case "desktop" -> banner.setDesktopUrl(imageUrl);
            default -> throw new IllegalArgumentException("Invalid device type: " + deviceType);
        }

        Banner updated = bannerRepository.save(banner);
        return mapToAdminResponse(updated);
    }

    /**
     * Quick status toggle (ACTIVE <-> PAUSED)
     */
    @Transactional
    public BannerAdminResponse toggleStatus(Long id) {
        Banner banner = bannerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Banner not found with id: " + id));

        if (banner.getStatus() == BannerStatus.ACTIVE) {
            banner.setStatus(BannerStatus.PAUSED);
        } else {
            banner.setStatus(BannerStatus.ACTIVE);
        }

        Banner updated = bannerRepository.save(banner);
        return mapToAdminResponse(updated);
    }

    /**
     * Update banner's status directly
     */
    @Transactional
    public BannerAdminResponse updateStatus(Long id, BannerStatus status) {
        Banner banner = bannerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Banner not found with id: " + id));

        banner.setStatus(status);
        Banner updated = bannerRepository.save(banner);
        return mapToAdminResponse(updated);
    }

    /**
     * Reorder banners (drag-and-drop functionality)
     */
    @Transactional
    public void reorderBanners(List<Long> bannerIds) {
        for (int i = 0; i < bannerIds.size(); i++) {
            Long bannerId = bannerIds.get(i);
            Banner banner = bannerRepository.findById(bannerId)
                    .orElseThrow(() -> new ResourceNotFoundException("Banner not found with id: " + bannerId));
            banner.setPriority(i + 1);
            bannerRepository.save(banner);
        }
    }

    /**
     * Soft delete banner
     */
    @Transactional
    public void deleteBanner(Long id) {
        Banner banner = bannerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Banner not found with id: " + id));
        banner.setDeletedAt(LocalDateTime.now());
        bannerRepository.save(banner);
    }

    // ============ PUBLIC OPERATIONS ============

    /**
     * Get active banners for public display
     * Automatically filters by current date and day of week
     */
    public List<PublicBannerResponse> getActiveBanners(BannerPlacement placement) {
        LocalDateTime now = LocalDateTime.now();
        String currentDay = DayOfWeek.from(now).getDisplayName(TextStyle.SHORT, Locale.ENGLISH).toUpperCase();

        List<Banner> banners = bannerRepository.findActiveBannersWithDayFilter(now, placement, currentDay);

        return banners.stream()
                .map(this::mapToPublicResponse)
                .collect(Collectors.toList());
    }

    /**
     * Track banner click
     */
    @Transactional
    public void trackClick(Long id) {
        bannerRepository.incrementClickCount(id);
    }

    // ============ ANALYTICS ============

    /**
     * Get top performing banners
     */
    public List<BannerAdminResponse> getTopPerformingBanners(int limit) {
        Pageable pageable = Pageable.ofSize(limit);
        return bannerRepository.findTopPerformingBanners(pageable).stream()
                .map(this::mapToAdminResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get banner analytics by placement
     */
    public long getActiveBannerCountByPlacement(BannerPlacement placement) {
        return bannerRepository.countActiveByPlacement(placement);
    }

    // ============ MAPPERS ============

    private BannerAdminResponse mapToAdminResponse(Banner banner) {
        BannerAdminResponse response = new BannerAdminResponse();
        response.setId(banner.getId());
        response.setMobileUrl(banner.getMobileUrl());
        response.setTabletUrl(banner.getTabletUrl());
        response.setDesktopUrl(banner.getDesktopUrl());
        response.setTitle(banner.getTitle());
        response.setLink(banner.getLink());
        response.setPlacement(banner.getPlacement());
        response.setStatus(banner.getStatus());
        response.setPriority(banner.getPriority());
        response.setTotalClicked(banner.getTotalClicked());
        response.setStartDate(banner.getStartDate());
        response.setEndDate(banner.getEndDate());
        response.setShowDays(banner.getShowDays());
        response.setCreatedAt(banner.getCreatedAt());
        response.setUpdatedAt(banner.getUpdatedAt());
        return response;
    }

    private PublicBannerResponse mapToPublicResponse(Banner banner) {
        PublicBannerResponse response = new PublicBannerResponse();
        response.setId(banner.getId());
        response.setMobileUrl(banner.getMobileUrl());
        response.setTabletUrl(banner.getTabletUrl());
        response.setDesktopUrl(banner.getDesktopUrl());
        response.setTitle(banner.getTitle());
        response.setLink(banner.getLink());
        response.setPlacement(banner.getPlacement());
        response.setPriority(banner.getPriority());
        response.setShowDays(banner.getShowDays());
        response.setStartDate(banner.getStartDate());
        response.setEndDate(banner.getEndDate());
        return response;
    }
}

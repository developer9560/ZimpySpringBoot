package com.zimpy.banner.Controller;

import com.zimpy.banner.dto.BannerAdminResponse;
import com.zimpy.banner.dto.BannerCreateRequest;
import com.zimpy.banner.dto.BannerUpdateRequest;
import com.zimpy.banner.entity.BannerPlacement;
import com.zimpy.banner.entity.BannerStatus;
import com.zimpy.banner.service.BannerService;
import com.zimpy.common.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/admin/banners")
@PreAuthorize("hasRole('ADMIN')")
public class AdminBannerController {

    @Autowired
    private BannerService bannerService;

    /**
     * CREATE - Upload a new banner with images
     * POST /admin/banners
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<BannerAdminResponse>> createBanner(
            @RequestPart("data") BannerCreateRequest request,
            @RequestPart(value = "mobileImage", required = false) MultipartFile mobileImage,
            @RequestPart(value = "tabletImage", required = false) MultipartFile tabletImage,
            @RequestPart(value = "desktopImage", required = false) MultipartFile desktopImage) {
        BannerAdminResponse response = bannerService.createBanner(request, mobileImage, tabletImage, desktopImage);
        return ResponseEntity.ok(new ApiResponse<>(201, "Banner created successfully", response));
    }

    /**
     * READ ALL - Get all banners (with pagination)
     * GET /admin/banners?page=0&size=10
     */
    @GetMapping
    public ResponseEntity<ApiResponse<Page<BannerAdminResponse>>> getAllBanners(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BannerAdminResponse> banners = bannerService.getAllBanners(pageable);
        return ResponseEntity.ok(new ApiResponse<>(200, "Banners fetched successfully", banners));
    }

    /**
     * READ - Get single banner by ID
     * GET /admin/banners/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BannerAdminResponse>> getBannerById(@PathVariable Long id) {
        BannerAdminResponse response = bannerService.getBannerById(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Banner fetched successfully", response));
    }

    /**
     * FILTER - Get banners by status
     * GET /admin/banners/status/{status}?page=0&size=10
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<Page<BannerAdminResponse>>> getBannersByStatus(
            @PathVariable BannerStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BannerAdminResponse> banners = bannerService.getBannersByStatus(status, pageable);
        return ResponseEntity.ok(new ApiResponse<>(200, "Banners filtered by status", banners));
    }

    /**
     * FILTER - Get banners by placement
     * GET /admin/banners/placement/{placement}?page=0&size=10
     */
    @GetMapping("/placement/{placement}")
    public ResponseEntity<ApiResponse<Page<BannerAdminResponse>>> getBannersByPlacement(
            @PathVariable BannerPlacement placement,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BannerAdminResponse> banners = bannerService.getBannersByPlacement(placement, pageable);
        return ResponseEntity.ok(new ApiResponse<>(200, "Banners filtered by placement", banners));
    }

    /**
     * UPDATE - Update banner metadata (without images)
     * PUT /admin/banners/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BannerAdminResponse>> updateBanner(
            @PathVariable Long id,
            @RequestBody BannerUpdateRequest request) {
        BannerAdminResponse response = bannerService.updateBanner(id, request);
        return ResponseEntity.ok(new ApiResponse<>(200, "Banner updated successfully", response));
    }

    /**
     * UPDATE IMAGE - Update banner image for specific device
     * PATCH /admin/banners/{id}/image/{deviceType}
     */
    @PatchMapping(value = "/{id}/image/{deviceType}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<BannerAdminResponse>> updateBannerImage(
            @PathVariable Long id,
            @PathVariable String deviceType,
            @RequestPart("image") MultipartFile image) {
        BannerAdminResponse response = bannerService.updateBannerImage(id, deviceType, image);
        return ResponseEntity.ok(new ApiResponse<>(200, "Banner image updated successfully", response));
    }

    /**
     * TOGGLE STATUS - Quick toggle between ACTIVE and PAUSED
     * PATCH /admin/banners/{id}/toggle
     */
    @PatchMapping("/{id}/toggle")
    public ResponseEntity<ApiResponse<BannerAdminResponse>> toggleStatus(@PathVariable Long id) {
        BannerAdminResponse response = bannerService.toggleStatus(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Banner status toggled", response));
    }

    /**
     * UPDATE STATUS - Set specific status
     * PATCH /admin/banners/{id}/status
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<BannerAdminResponse>> updateStatus(
            @PathVariable Long id,
            @RequestParam BannerStatus status) {
        BannerAdminResponse response = bannerService.updateStatus(id, status);
        return ResponseEntity.ok(new ApiResponse<>(200, "Banner status updated", response));
    }

    /**
     * REORDER - Update banner priorities (drag-and-drop)
     * POST /admin/banners/reorder
     * Body: [1, 3, 2, 5, 4] (order of banner IDs)
     */
    @PostMapping("/reorder")
    public ResponseEntity<ApiResponse<Void>> reorderBanners(@RequestBody List<Long> bannerIds) {
        bannerService.reorderBanners(bannerIds);
        return ResponseEntity.ok(new ApiResponse<>(200, "Banners reordered successfully", null));
    }

    /**
     * DELETE - Soft delete banner
     * DELETE /admin/banners/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBanner(@PathVariable Long id) {
        bannerService.deleteBanner(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Banner deleted successfully", null));
    }

    /**
     * ANALYTICS - Get top performing banners
     * GET /admin/banners/analytics/top?limit=10
     */
    @GetMapping("/analytics/top")
    public ResponseEntity<ApiResponse<List<BannerAdminResponse>>> getTopPerformingBanners(
            @RequestParam(defaultValue = "10") int limit) {
        List<BannerAdminResponse> banners = bannerService.getTopPerformingBanners(limit);
        return ResponseEntity.ok(new ApiResponse<>(200, "Top performing banners", banners));
    }

    /**
     * ANALYTICS - Get banner count by placement
     * GET /admin/banners/analytics/count/{placement}
     */
    @GetMapping("/analytics/count/{placement}")
    public ResponseEntity<ApiResponse<Long>> getBannerCountByPlacement(@PathVariable BannerPlacement placement) {
        long count = bannerService.getActiveBannerCountByPlacement(placement);
        return ResponseEntity.ok(new ApiResponse<>(200, "Active banner count", count));
    }
}

package com.zimpy.banner.Controller;

import com.zimpy.banner.dto.PublicBannerResponse;
import com.zimpy.banner.entity.BannerPlacement;
import com.zimpy.banner.service.BannerService;
import com.zimpy.common.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Public Banner Controller
 * No authentication required - accessible to all users
 */
@RestController
@RequestMapping("/public/banners")
public class PublicBannerController {

    @Autowired
    private BannerService bannerService;

    /**
     * Get active banners for homepage or specific placement
     * GET /api/banners?placement=HOME_TOP
     * 
     * Automatically filters by:
     * - ACTIVE status
     * - Current date within schedule
     * - Current day of week (if restricted)
     * - Priority order
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<PublicBannerResponse>>> getActiveBanners(
            @RequestParam(required = false) BannerPlacement placement) {
        List<PublicBannerResponse> banners = bannerService.getActiveBanners(placement);
        return ResponseEntity.ok(new ApiResponse<>(200, "Active banners retrieved", banners));
    }

    /**
     * Get active banners for a specific placement
     * GET /api/banners/placement/HOME_TOP
     */
    @GetMapping("/placement/{placement}")
    public ResponseEntity<ApiResponse<List<PublicBannerResponse>>> getBannersByPlacement(
            @PathVariable BannerPlacement placement) {
        List<PublicBannerResponse> banners = bannerService.getActiveBanners(placement);
        return ResponseEntity.ok(new ApiResponse<>(200, "Banners for " + placement, banners));
    }

    /**
     * Track banner click (for analytics)
     * POST /api/banners/{id}/click
     */
    @PostMapping("/{id}/click")
    public ResponseEntity<ApiResponse<Void>> trackClick(@PathVariable Long id) {
        bannerService.trackClick(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Click tracked", null));
    }
}

package com.roomster.roomsterbackend.controller.user;

import com.roomster.roomsterbackend.service.IService.IWishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/wishlist")
@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_MANAGE','ROLE_ADMIN')")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class WishListController {

    private final IWishListService wishListService;

    @PostMapping(value = "/add")
    public ResponseEntity<?> addWishList(@RequestParam Long postId, @RequestParam String wishlistName, Principal connectedUser){
        return wishListService.addPostToWishlist(connectedUser, postId, wishlistName);
    }

    @GetMapping(value = "/get")
    public ResponseEntity<?> getWishListByNameAndUser(Principal connectedUser,@RequestParam String wishListName){
        return wishListService.getWishListByNameAndUser(connectedUser, wishListName);
    }

    @DeleteMapping(value = "delete/wishListItem/{wishListItemId}")
    public ResponseEntity<?> deleteWishListItem(@PathVariable Long wishListItemId){
        return wishListService.deleteWishListItem(wishListItemId);
    }
}

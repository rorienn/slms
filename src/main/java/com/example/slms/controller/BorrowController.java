package com.example.slms.controller;

import com.example.slms.entity.BorrowRecord;
import com.example.slms.entity.User;
import com.example.slms.service.BorrowService;
import com.example.slms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/borrow")
public class BorrowController {

    @Autowired
    private BorrowService borrowService;

    @Autowired
    private UserService userService;

    @PostMapping
    //Authorization could happen here as well: @PreAuthorize("hasRole('CLIENT')")
    public BorrowRecord borrowBook(@AuthenticationPrincipal final UserDetails userDetails, @RequestParam final UUID bookId) {
        //Could be implemented in a try/catch instead of using Optional<T>
        User user = userService.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return borrowService.borrowBook(user, bookId);
    }


}


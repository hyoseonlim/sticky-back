package com.sticky.sticky_back.user.controller;

import com.sticky.sticky_back.user.datamodel.MemberEntity;
import com.sticky.sticky_back.user.dto.UserSignupRequest;
import com.sticky.sticky_back.user.dto.UserSignupResponse;
import com.sticky.sticky_back.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 회원가입
     * POST /api/user
     */
    @PostMapping
    public ResponseEntity<UserSignupResponse> signup(@RequestBody UserSignupRequest request) {
        UserSignupResponse response = userService.signup(request);
        return ResponseEntity.ok(response);
    }

    /**
     * 로그인 (간단 조회)
     * GET /api/user?studentId=xxx 또는 GET /api/user?email=xxx
     */
    @GetMapping
    public ResponseEntity<UserSignupResponse> login(
            @RequestParam(required = false) String email) {

        MemberEntity member;
        if (email != null) {
            member = userService.findByEmail(email);
        } else {
            throw new RuntimeException("studentId or email is required");
        }

        UserSignupResponse response = UserSignupResponse.builder()
                .uuid(member.getUuid())
                .nickname(member.getNickname())
                .name(member.getName())
                .email(member.getEmail())
                .level(member.getLevel().getValue())
                .rate(member.getRate())
                .build();

        return ResponseEntity.ok(response);
    }
}

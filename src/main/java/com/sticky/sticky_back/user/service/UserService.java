package com.sticky.sticky_back.user.service;

import com.sticky.sticky_back.user.datamodel.MemberEntity;
import com.sticky.sticky_back.user.datamodel.MemberLevel;
import com.sticky.sticky_back.user.datamodel.MemberStatus;
import com.sticky.sticky_back.user.dto.UserSignupRequest;
import com.sticky.sticky_back.user.dto.UserSignupResponse;
import com.sticky.sticky_back.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    /**
     * 회원가입
     */
    @Transactional
    public UserSignupResponse signup(UserSignupRequest request) {
        // Entity 생성
        MemberEntity member = MemberEntity.builder()
                .studentId(request.getStudentId())
                .email(request.getEmail())
                .password(request.getPassword())
                .name(request.getName())
                .tel(request.getTel())
                .level(MemberLevel.LEVEL_1)
                .rate(0.0)
                .build();

        // 저장
        MemberEntity savedMember = userRepository.save(member);

        // Response 변환
        return UserSignupResponse.builder()
                .uuid(savedMember.getUuid())
                .studentId(savedMember.getStudentId())
                .name(savedMember.getName())
                .email(savedMember.getEmail())
                .level(savedMember.getLevel().getValue())
                .rate(savedMember.getRate())
                .build();
    }

    /**
     * 학번으로 회원 조회
     */
    public MemberEntity findByStudentId(String studentId) {
        return userRepository.findByStudentId(studentId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    /**
     * 이메일로 회원 조회
     */
    public MemberEntity findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    /**
     * UUID로 회원 조회
     */
    public MemberEntity findByUuid(Integer uuid) {
        return userRepository.findById(uuid)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}

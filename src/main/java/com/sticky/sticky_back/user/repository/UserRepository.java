package com.sticky.sticky_back.user.repository;

import com.sticky.sticky_back.user.datamodel.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<MemberEntity, Integer> {

    Optional<MemberEntity> findByStudentId(String studentId);

    Optional<MemberEntity> findByEmail(String email);
}

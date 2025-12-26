package com.sticky.sticky_back.user.datamodel;

import com.sticky.sticky_back.user.datamodel.MemberLevel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uuid")
    private Integer uuid;

    @Column(name = "student_id", nullable = false, length = 50)
    private String studentId;

    @Column(name = "password", length = 50)
    private String password;

    @Column(name = "tel", length = 20)
    private String tel;

    @Column(name = "kakao_tel", length = 50)
    private String kakaoTel;

    @Column(name = "name", length = 20)
    private String name;

    @Column(name = "email", unique = true, length = 30)
    private String email;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "level")
    private MemberLevel level;

    @Column(name = "rate")
    @Builder.Default
    private Double rate = 0.0;

    @Column(name = "kakao_uuid", unique = true)
    private Integer kakaoUuid;
}

package com.shop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import com.shop.dto.MemberFormDto;
import com.shop.entity.Member;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Member createMember() {
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setName("John Woo");
        memberFormDto.setPassword("1234");
        memberFormDto.setEmail("test@email.com");
        memberFormDto.setAddress("서울시 강서구 가양동");

        return Member.createMember(memberFormDto, passwordEncoder);
    }

    @Test
    @DisplayName("회원가입 테스트")
    public void saveMemberTest() {
        Member member = createMember();
        Member saveMember = memberService.saveMember(member);

        assertEquals(member.getRole(), saveMember.getRole());
        assertEquals(member.getName(), saveMember.getName());
        assertEquals(member.getEmail(), saveMember.getEmail());
        assertEquals(member.getAddress(), saveMember.getAddress());
        assertEquals(member.getPassword(), saveMember.getPassword());
    }
}

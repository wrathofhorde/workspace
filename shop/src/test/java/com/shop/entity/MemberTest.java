package com.shop.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import com.shop.repository.MemberRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class MemberTest {

    @Autowired
    private MemberRepository memberRepository;

    @PersistenceContext
    private EntityManager em;

    @Test
    @DisplayName("Auditing Test")
    @WithMockUser(username = "user", roles = "USER")
    public void auditing() {
        Member newMember = new Member();
        memberRepository.save(newMember);

        em.flush();
        em.clear();

        Member member = memberRepository
                .findById(newMember.getId())
                .orElseThrow(EntityNotFoundException::new);

        System.out.println("등록일: " + member.getRegTime());
        System.out.println("변경일: " + member.getUpDateTime());
        System.out.println("생성자: " + member.getCreatedBy());
        System.out.println("변경자: " + member.getModifiedBy());
    }
}

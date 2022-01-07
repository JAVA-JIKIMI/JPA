package jpabook.jpashop;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") // disable autowired error
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional // rollback when used with @Test
    // if need rollback off, use -> @Rollback(value = false)
    @Rollback(value = false)
    public void testMember() {
        // given
        final Member member = new Member(); // use final
        member.setUsername("memberA");

        // when
        final long savedId = memberRepository.save(member);
        final Member findMember = memberRepository.find(savedId);

        // then
        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member); // same persistence context (1st cache)
    }

}

package jpabook.jpashop;

import jpabook.jpashop.domain.Member;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest // disable autowired error
class MemberRepositoryTest {

    @Autowired
    MemberRepositoryWeek2 memberRepository;

    @Test
    @Transactional // rollback when used with @Test
    // if need rollback off, use -> @Rollback(value = false)
    public void testMember() {
        // given
        final Member member = new Member(); // use final
        member.setName("memberA");

        // when
        final long savedId = memberRepository.save(member);
        final Member findMember = memberRepository.find(savedId);

        // then
        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getName()).isEqualTo(member.getName());
        assertThat(findMember).isEqualTo(member); // same persistence context (1st cache)
    }

}

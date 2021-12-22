package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository //컴포넌트 스캔으로 자동으로 스프링 빈으로 관리됨
@RequiredArgsConstructor
public class MemberRepository {

    //@PersistenceContext //스프링이 엔티티매니저를 만들어서 injection 해줌
    //private EntityManager em;

    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id); // 두번째값은 pk
    }

    public List<Member> findAll() { //jpql은 sql이랑 기능적으로는 동일 sql은 테이블대상 jpql은 엔티티 객체 대상
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }

}

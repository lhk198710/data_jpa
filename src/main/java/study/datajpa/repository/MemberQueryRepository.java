package study.datajpa.repository;

import org.springframework.stereotype.Repository;
import study.datajpa.entity.Member;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * 항상 사용자 리포지토리가 필요한 것은 아님.
 * View를 보여주거나 다수의 JOIN이 들어가는 것과 같은 복잡한 쿼리는 쿼리용으로 따로 분리하여 만들기도 함.
 * 구조가 커질 수록 커맨드와 쿼리를 분리 하는 것, 핵심 비지니스와 아닌 것을 분리하는 것에 대해서 고민을 하면서 클래스들을 분리 시킬 필요가 있음.
 */

@Repository
public class MemberQueryRepository {
    private final EntityManager em;

    public MemberQueryRepository(EntityManager em) {
        this.em = em;
    }

    public List<Member> findAllMembers() {
        return em.createQuery("select m from Member m").getResultList();
    }
}

package study.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.datajpa.entity.Member;

import javax.annotation.PreDestroy;
import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    // 파라미터가 길어지게 되면 가독성이 떨어지고 관리가 힘들어짐. 간단한 조회에 대해서는 쓰기 편함.
    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    List<Member> findTop3HelloBy();

    @Query(name = "Member.findByUsername")
    List<Member> findByUsername(@Param("username") String username);

    // 복잡한 쿼리 조회 필요 시 사용
    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);
}

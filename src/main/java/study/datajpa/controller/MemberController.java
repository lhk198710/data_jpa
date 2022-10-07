package study.datajpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.repository.MemberRepository;

import javax.annotation.PostConstruct;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberRepository memberRepository;

    @GetMapping("/members/{id}")
    public String findMember(@PathVariable("id") Long id) {
        Member member = memberRepository.findById(id).get();
        return  member.getUsername();
    }

    /**
     * HTTP 요청은 회원 id를 받지만 도메인 클래스 컨버터가 중간에 동작해서 회원 엔티티 객체를 반환
     * 도메인 클래스 컨버터도 리파지토리를 사용해서 엔티티를 찾음
     * 주의 : 도메인클래스 컨버터로 엔티티를 파라미터로 바등면, 이 엔티티는 단순 조회용으로만 사용해야함.
     *  - 트랜잭션이 없는 범위에서 엔티티를 조회했으므로, 엔티티를 변경해도 DB에 반영되지 않는다.
     */
    @GetMapping("/members2/{id}")
    public String findMember(@PathVariable("id") Member member) {
        return  member.getUsername();
    }

    @GetMapping("/members")
    public Page<MemberDto> list(Pageable pageable) {
        /**
         * 참고
         * @PageableDefault : 기본 Pageable의 룰을 내 입맛에 맞게 지정.
         */

        /**
         * http://localhost:8080/members?page=0 : 0번 인덱스부터 20개 꺼냄.
         * http://localhost:8080/members?page=1 : 21번부터 나옴.
         * http://localhost:8080/members?page=0&size=3 : 0번 인덱스부터 시작, 3개만 출력.
         * http://localhost:8080/members?page=0&size=3&sord=id,desc : 3개만 출력. id 기준 desc 정렬. sort는 기본이 asc라 asc는 생략 가능.
         */

        Page<Member> page = memberRepository.findAll(pageable);
        // Page<MemberDto> toMap = page.map(m -> new MemberDto(m.getId(), m.getUsername(), null)); // Entity를 그대로 노출시키지 않기 위함.
        Page<MemberDto> toMap = page.map(MemberDto::new);
        return toMap;
    }

    @PostConstruct
    public void init() {
        for (int i=0; i<100; i++) {
            memberRepository.save(new Member("user" + i, i));
        }
    }
}

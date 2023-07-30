package hello.core.domain;

import hello.core.domain.Member;

public interface MemberRepository {
    void save(Member member);

    Member findById(Long id);
}

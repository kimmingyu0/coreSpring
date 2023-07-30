package hello.core.domain;

import hello.core.domain.Member;

public interface MemberService {
    void join(Member member);

    Member findMember(Long id);
}

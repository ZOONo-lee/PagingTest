package ds.com.notice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ds.com.notice.entity.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long>, // JpaRepository를 상속받는 인터페이스
        QuerydslPredicateExecutor<Notice> { // QuerydslPredicateExecutor이건 querydsl을 이용하게 되면 상속해야함! 그리고 q로
                                            // 시작하는 클래스는 개발자가 건드리면안됨!
}

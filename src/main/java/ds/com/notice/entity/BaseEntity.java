package ds.com.notice.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;

@MappedSuperclass // 이게 적용된 클래스는 테이블로 생성되지 않음
@EntityListeners(value = { AuditingEntityListener.class }) // AuditingEntityListener = jpa 내부에서 엔티티 객체가 생성변경되는걸 감지하는 역할.
                                                           // 이를 통해 regdate, moddate에 적절한 값 지정
                                                           // 이걸 활성화시키려면 noticeapplicateion에 @Enablejpaauditing설정
                                                           // 추가해야함
@Getter
public abstract class BaseEntity { // 실제 테이블은 BaseEntity를 상속한 엔티티의 클래스로 db테이블이 생성
  @CreatedDate // (jpa에서)엔티티의 생성 시간을 처리!
  @Column(name = "regdate", updatable = false) // updatable = false는 해당 엔티티에서 객체를 데이터베이스에 반영할때 regdate칼럼값은 변경안함
  private LocalDateTime regDate;

  @LastModifiedDate // 최종 수정 시간을 자동으로 처리
  @Column(name = "moddate")
  private LocalDateTime modDate;
}

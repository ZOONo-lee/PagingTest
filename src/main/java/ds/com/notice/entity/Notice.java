package ds.com.notice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Notice extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long gno; // 고유번호

  @Column(length = 100, nullable = false)
  private String title; // 제목

  @Column(length = 1500, nullable = false)
  private String content; // 내용

  @Column(length = 50, nullable = false)
  private Long uno;

  public void changeTitle(String title) {
    this.title = title;
  } // 제목변경

  public void changeContent(String content) {
    this.content = content;
  } // 내용변경

}

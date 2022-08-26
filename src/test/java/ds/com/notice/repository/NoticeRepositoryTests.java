package ds.com.notice.repository;

import java.util.Optional;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ds.com.notice.entity.Notice;
import ds.com.notice.entity.QNotice;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;

@SpringBootTest
public class NoticeRepositoryTests {

  @Autowired
  private NoticeRepository repository;

  @Test
  public void insertDummies() { // 데이터베이스에 더미 삽입 테스트
    IntStream.rangeClosed(1, 100).forEach(new IntConsumer() { // 300개의 데이터 넣기
      @Override
      public void accept(int i) {
        Notice gb = Notice.builder().title("Title..." + i) // 제목 : 제목...(1~100)
            .content("Content" + i).uno(i % 10L).build(); // 내용+1~100 + uno:user1~100/10
        System.out.println(repository.save(gb)); // NoticeRepository저장
      }
    });
  }

  @Test
  public void updateTest() {
    Optional<Notice> result = repository.findById(300L);
    if (result.isPresent()) {
      Notice gb = result.get();
      gb.changeTitle("Changed Title...");
      gb.changeContent("Changed Content...");
      repository.save(gb);
    }
  }

  @Test
  public void testQuery1() {
    Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());

    QNotice qNotice = QNotice.notice; // 1
    String keyword = "1";
    BooleanBuilder builder = new BooleanBuilder(); // 2 질의하기 위한 객체
    BooleanExpression expression = qNotice.title.contains(keyword); // 3 질의 식
    builder.and(expression); // 4 객체가 and로 질의 식을 실행
    Page<Notice> result = repository.findAll(builder, pageable);
    result.stream().forEach(gb -> {
      System.out.println(gb);
    });
  }

  @Test
  public void testQuery2() {
    Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());

    QNotice qNotice = QNotice.notice; // 1
    String keyword = "1";
    BooleanBuilder builder = new BooleanBuilder(); // 2 질의하기 위한 객체

    BooleanExpression exTitle = qNotice.title.contains(keyword); // 3 질의 식
    BooleanExpression exContent = qNotice.content.contains(keyword); // 3 질의 식
    BooleanExpression exAll = exTitle.or(exContent);

    builder.and(exAll); // 4 객체가 and로 질의 식을 실행
    builder.and(qNotice.gno.gt(0L));
    Page<Notice> result = repository.findAll(builder, pageable);
    result.stream().forEach(gb -> {
      System.out.println(gb);
    });
  }

}

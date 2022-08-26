package ds.com.notice.service;

import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ds.com.notice.dto.NoticeDTO;
import ds.com.notice.dto.PageRequestDTO;
import ds.com.notice.dto.PageResultDTO;
import ds.com.notice.entity.Notice;
import ds.com.notice.entity.QNotice;
import ds.com.notice.repository.NoticeRepository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

  private final NoticeRepository gbRepository;

  @Override
  public NoticeDTO read(Long gno) {
    Optional<Notice> result = gbRepository.findById(gno);
    return result.isPresent() ? entityToDTO(result.get()) : null;
  }

  @Override
  public Long register(NoticeDTO dto) {
    log.info("register dto:" + dto);
    Notice entity = dtoToEntity(dto);
    gbRepository.save(entity);
    return entity.getGno();
  }

  @Override
  public PageResultDTO<NoticeDTO, Notice> getList(PageRequestDTO requestDTO) {
    Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());
    BooleanBuilder booleanBuilder = getSearch(requestDTO);
    Page<Notice> result = gbRepository.findAll(booleanBuilder, pageable);
    Function<Notice, NoticeDTO> fn = new Function<Notice, NoticeDTO>() {
      @Override
      public NoticeDTO apply(Notice entity) {
        return entityToDTO(entity);
      }
    };
    return new PageResultDTO<>(result, fn);
  }

  @Override
  public void remove(Long gno) {
    log.info("remove... " + gno);
    gbRepository.deleteById(gno);
  }

  @Override
  public void modify(NoticeDTO dto) {
    log.info("modify... " + dto);
    // Entity를 먼저 찾는 이유: Entity가 있어야 부분 변경이 가능.
    Optional<Notice> result = gbRepository.findById(dto.getGno());
    if (result.isPresent()) {
      Notice entity = result.get();
      entity.changeTitle(dto.getTitle()); // 부분만 바꿈
      entity.changeContent(dto.getContent()); // 부분만 바꿈
      gbRepository.save(entity);
    }
  }

  private BooleanBuilder getSearch(PageRequestDTO requestDTO) {
    String type = requestDTO.getType();
    String keyword = requestDTO.getKeyword();

    BooleanBuilder booleanBuilder = new BooleanBuilder();
    QNotice qNotice = QNotice.notice; // 관련된 테이블에 대한 쿼리 객체
    BooleanExpression expression = qNotice.gno.gt(0L);
    booleanBuilder.and(expression);
    if (type == null || type.trim().length() == 0)
      return booleanBuilder; // 검색조건無
    BooleanBuilder conditionBuilder = new BooleanBuilder();

    if (type.contains("t"))
      conditionBuilder.or(qNotice.title.contains(keyword));
    if (type.contains("c"))
      conditionBuilder.or(qNotice.content.contains(keyword));
    /*
     * if (type.contains("w"))
     * conditionBuilder.or(qNotice.uno.contains(keyword));
     */
    booleanBuilder.and(conditionBuilder);
    return booleanBuilder;
  }
}

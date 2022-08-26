package ds.com.notice.service;

import ds.com.notice.dto.NoticeDTO;
import ds.com.notice.dto.PageRequestDTO;
import ds.com.notice.dto.PageResultDTO;
import ds.com.notice.entity.Notice;

public interface NoticeService {
  Long register(NoticeDTO dto);

  PageResultDTO<NoticeDTO, Notice> getList(PageRequestDTO requestDTO);

  NoticeDTO read(Long gno);

  void remove(Long gno);

  void modify(NoticeDTO dto);

  default Notice dtoToEntity(NoticeDTO dto) {
    Notice entity = Notice.builder().gno(dto.getGno())
        .title(dto.getTitle()).content(dto.getContent())
        .uno(dto.getUno()).build();
    return entity;
  }

  default NoticeDTO entityToDTO(Notice entity) {
    NoticeDTO dto = NoticeDTO.builder().gno(entity.getGno())
        .title(entity.getTitle()).content(entity.getContent())
        .uno(entity.getUno()).regDate(entity.getRegDate())
        .modDate(entity.getModDate()).build();
    return dto;
  }
}

package ds.com.notice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ds.com.notice.dto.NoticeDTO;
import ds.com.notice.dto.PageRequestDTO;
import ds.com.notice.service.NoticeService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("notice")
@Log4j2
@AllArgsConstructor
public class NoticeController {
  private final NoticeService gbService;

  @GetMapping({ "/" })
  public String index() {
    log.info("index...........");
    return "redirect:/notice/list";
  }

  @GetMapping({ "/list" })
  public void list(@ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {
    log.info("list...........");
    model.addAttribute("result", gbService.getList(requestDTO));
  }

  @GetMapping("/register")
  public void register() {
    log.info("regist.......");
  }

  @PostMapping("/register")
  public String registerPost(NoticeDTO dto, RedirectAttributes ra) {
    log.info("register post.........");
    Long gno = gbService.register(dto);
    ra.addFlashAttribute("msg", gno + " 등록");
    return "redirect:/notice/list";
  }

  @GetMapping({ "/read", "/modify" })
  public void read(long gno, Model model,
      @ModelAttribute("requestDTO") PageRequestDTO requestDTO) {
    // 커맨드 객체로 받은 것은 다음 페이지에도 넘어감.
    log.info("read ....... gno:" + gno);
    NoticeDTO dto = gbService.read(gno);
    model.addAttribute("dto", dto);
  }

  @PostMapping("/remove")
  public String remove(long gno, RedirectAttributes ra,
      PageRequestDTO requestDTO) {
    log.info("remove..." + gno);
    gbService.remove(gno);
    ra.addFlashAttribute("msg", gno + " 삭제");
    ra.addAttribute("page", requestDTO.getPage());
    ra.addAttribute("type", requestDTO.getType());
    ra.addAttribute("keyword", requestDTO.getKeyword());
    return "redirect:/notice/list";
  }

  @PostMapping("/modify")
  public String modifyPost(NoticeDTO dto, RedirectAttributes ra,
      PageRequestDTO requestDTO) {
    log.info("modifyPost..." + dto);
    gbService.modify(dto);
    ra.addFlashAttribute("msg", dto.getGno() + " 수정");
    ra.addAttribute("gno", dto.getGno());
    ra.addAttribute("type", requestDTO.getType());
    ra.addAttribute("keyword", requestDTO.getKeyword());
    ra.addAttribute("page", requestDTO.getPage());
    return "redirect:/notice/read";
  }
}

package ds.com.notice.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ds.com.notice.dto.NoticeDTO;
import ds.com.notice.dto.PageRequestDTO;
import ds.com.notice.dto.PageResultDTO;
import ds.com.notice.entity.Notice;

@SpringBootTest
public class NoticeServiceImplTests {
    @Autowired
    private NoticeService service;

    @Test
    void testRegister() {
        NoticeDTO dto = NoticeDTO.builder().title("Sample Title...")
                .content("Sample Content...").uno(0L).build();
        System.out.println("gno:::" + service.register(dto));
    }

    @Test
    public void testList() {
        PageRequestDTO requestDTO = PageRequestDTO.builder().page(1).size(10).build();
        PageResultDTO<NoticeDTO, Notice> resultDTO = service.getList(requestDTO);
        System.out.println("prev: " + resultDTO.isPrev());
        System.out.println("next: " + resultDTO.isNext());
        System.out.println("Total: " + resultDTO.getTotalPage());
        for (NoticeDTO dto : resultDTO.getDtoList()) {
            System.out.println(dto);
        }
        System.out.println("--------------------------------");
        resultDTO.getPageList().forEach(i -> System.out.println(i + ""));
    }
}
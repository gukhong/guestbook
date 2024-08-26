package com.gukhong.guestbook.service;

import com.gukhong.guestbook.dto.GuestbookDTO;
import com.gukhong.guestbook.dto.PageRequestDTO;
import com.gukhong.guestbook.dto.PageResultDTO;
import com.gukhong.guestbook.entity.Guestbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GuestbookServiceTests {

    @Autowired
    private GuestbookService service;

    @Test
    public void testRegister() {

        GuestbookDTO guestbookDTO = GuestbookDTO.builder()
                .title("Sample title")
                .content("Sample content")
                .writer("testuser")
                .build();

        System.out.println(service.register(guestbookDTO));
    }

    @Test
    public void testList(){

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(10).build();

        PageResultDTO<GuestbookDTO, Guestbook> resultDTO = service.getList(pageRequestDTO);

        for (GuestbookDTO guestbookDTO :resultDTO.getDtoList()) {
            System.out.println(guestbookDTO);

        }
    }
}

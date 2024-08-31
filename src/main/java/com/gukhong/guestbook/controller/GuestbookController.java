package com.gukhong.guestbook.controller;

import org.springframework.ui.Model;
import com.gukhong.guestbook.dto.PageRequestDTO;
import com.gukhong.guestbook.service.GuestbookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/guestbook")
@Log4j2
@RequiredArgsConstructor // 자동 주입을 위한 Annotation
public class GuestbookController {

    private final GuestbookService service; // final 선언

    @GetMapping("/")
    public String index(){
        return "redirect:/guestbook/list";
    }

    @GetMapping({"/list"})
    public void list(PageRequestDTO pageRequestDTO, Model model){

        log.info("list............."+ pageRequestDTO);

        model.addAttribute("result", service.getList(pageRequestDTO));
    }
}

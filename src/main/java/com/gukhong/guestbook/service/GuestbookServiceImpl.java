package com.gukhong.guestbook.service;

import com.gukhong.guestbook.dto.GuestbookDTO;
import com.gukhong.guestbook.dto.PageRequestDTO;
import com.gukhong.guestbook.dto.PageResultDTO;
import com.gukhong.guestbook.entity.Guestbook;
import com.gukhong.guestbook.repository.GuestbookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor  // 의존성 자동 주입
public class GuestbookServiceImpl implements GuestbookService{

    private final GuestbookRepository repository; // final 선언
    @Override
    public Long register(GuestbookDTO dto) {

        log.info("DTO-------------------------");
        log.info(dto);

        Guestbook entity = dtoToEntity(dto);

        log.info(entity);

        repository.save(entity);

        return entity.getGno();
    }

    @Override
    public PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO){
        Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());

        Page<Guestbook> result = repository.findAll(pageable);

        Function<Guestbook, GuestbookDTO> fn = (entity -> entityToDTO(entity));

        return new PageResultDTO<>(result, fn);
    }

    @Override
    public GuestbookDTO read(Long gno) {
        Optional<Guestbook> result = repository.findById(gno);

        return result.isPresent()? entityToDTO(result.get()): null;
    }
}

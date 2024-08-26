package com.gukhong.guestbook.repository;

import com.gukhong.guestbook.entity.Guestbook;
import com.gukhong.guestbook.entity.QGuestbook;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class GuestbookRepositoryTests {

    @Autowired
    private GuestbookRepository guestbookRepository;

    @Test
    public void insertDummies(){

        IntStream.rangeClosed(1,300).forEach(i -> {
            Guestbook guestbook = Guestbook.builder()
                    .title("Title..." + i )
                    .content("Content..." + i)
                    .writer("user" + (i % 10))
                    .build();
            System.out.println(guestbookRepository.save(guestbook));
        });
    }

    @Test
    public void updateTest(){
        Optional<Guestbook> result = guestbookRepository.findById(300L);

        if(result.isPresent()){

            Guestbook guestbook = result.get();

            guestbook.changeTitle("Changed Title...");
            guestbook.changeContent("Changed Content...");

            guestbookRepository.save(guestbook);

        }
    }

    // Querydsl Test
    // 1. 단일 항목 검색 테스트: title에 1이라는 글자가 있는 엔티티들을 검색하기
    @Test
    public void testQuery1(){
        Pageable pageable = PageRequest.of(0 ,10, Sort.by("gno").descending());

        QGuestbook qGuestbook = QGuestbook.guestbook; // 동적 처리를 위해 Q도메인 클래스 가져오기
        // Q도메인 클래스를 이용해서 엔티티 클래스에 선언된 title, content 같은 필드들을 변수로 활용

        String keyword = "1";

        BooleanBuilder builder = new BooleanBuilder(); // where에 들어가는 조건들을 넣어주는 컨테이너

        BooleanExpression expression = qGuestbook.title.contains(keyword);

        builder.and(expression);

        Page<Guestbook> result = guestbookRepository.findAll(builder, pageable);

        result.stream().forEach(guestbook ->{ 
            System.out.println(guestbook);
        });
    }
    
    // 2. 다중 항목 검색 테스트: 'title 이나 content에 특정 keyword가 있고 gno가 0보다 크다' 조건 처리
    @Test
    public void testQuery2(){
        
        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());
        
        QGuestbook qGuestbook = QGuestbook.guestbook;
        
        String keyword = "1";
        
        BooleanBuilder builder = new BooleanBuilder();
        
        BooleanExpression exTitle = qGuestbook.title.contains(keyword);

        BooleanExpression exContent = qGuestbook.content.contains(keyword);

        BooleanExpression exAll = exTitle.or(exContent);

        builder.and(exAll);

        builder.and(qGuestbook.gno.gt(0L));

        Page<Guestbook> result = guestbookRepository.findAll(builder, pageable);

        result.stream().forEach(guestbook ->{
            System.out.println(guestbook);
        });
    }
}

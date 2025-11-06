package jpabook.jpashop2.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop2.domain.item.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemUpdateTest {

    @Autowired EntityManager em;

    @Test
    public void 업데이트_테스트() throws Exception {
        Book book = em.find(Book.class, 1L);

        //transaction
        book.setName("밥말리");

        // 변경 감지 (dirty checking)
        // transaction commit
    }
}
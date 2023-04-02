package com.library.repository;

import com.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query(
            value = "SELECT * FROM book s where  s. category_id = :cateId",
            nativeQuery = true
    )
    List<Book> getAllBookByCategoryID(Long cateId);

    @Query(
            value = "SELECT * from book b " +
                    "where b.author like %:keyword% " +
                    " or b.detail like %:keyword% " +
                    " or b.publisher like %:keyword% " +
                    " or b.subject like %:keyword% " +
                    " or b.title like %:keyword% " +
                    " or b.amount like %:keyword% " +
                    " or b.borrow_price like %:keyword% ",
            nativeQuery = true
    )
    List<Book> getAllBooksByKeyword(String keyword);

}

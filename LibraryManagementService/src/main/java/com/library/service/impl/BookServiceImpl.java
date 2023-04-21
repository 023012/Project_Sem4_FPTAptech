package com.library.service.impl;

import com.library.entity.Book;
import com.library.repository.AttachmentRepository;
import com.library.repository.BookRepository;
import com.library.service.AttachmentService;
import com.library.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Tuple;
import javax.swing.text.html.parser.Entity;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
@Slf4j
public class BookServiceImpl implements BookService {
    private final AttachmentRepository attachmentRepository;
    private BookRepository bookRepository;

    private AttachmentService attachmentService;

    private EntityManagerFactory emf;
    private Long cateID;
    private String keyword;

    public BookServiceImpl(BookRepository bookRepository , EntityManagerFactory emf,
                           AttachmentRepository attachmentRepository){
        this.bookRepository = bookRepository;
        this.emf = emf;

        this.attachmentRepository = attachmentRepository;
    }

    @Override
    public Book createBook(Book book) {
        Calendar cal = Calendar.getInstance();
        book.setCreatedAt(cal.getTime());
        book.setUpdatedAt(cal.getTime());
        book.setStatus(Book.BookStatus.UNAVAILABLE);
        return bookRepository.save(book);
    }

    @Override
    public List<Book> getAllBooks() {
        log.info("Dang tim tat ca books");
        return bookRepository.findAll();
    }

    @Override
    public List<Book> getAllBookByCategoryID(Long cateID) {
        log.info("Dang tim tat ca books bang cateID ");
        return bookRepository.getAllBookByCategoryID(cateID);
    }
    @Override
    public List<Book> getAllBookByCateIDAndKeyword(Long cateID, String keyword){
        this.cateID = cateID;
        this.keyword = keyword;
        List<Book> getByKeyword = bookRepository.getAllBooksByKeyword(keyword);
        List<Book> getByCateIDAndKeyword = new ArrayList<Book>();
        for (Book book : getByKeyword){
            if(book.getCategory().getCategoryId() == cateID){
                getByCateIDAndKeyword.add(book);
            }
        }
        return getByCateIDAndKeyword;
    }

    @Override
    public List<Book> getListBook_InOrder(String orderId) {
        return null;
    }

    @Override
    public List<Book> getAllBookByKeyword(String keyword) {
        return bookRepository.getAllBooksByKeyword(keyword);
    }


    @Override
    public String deleteBook(Long id) {
        Book book = bookRepository.findById(id).get();
        if(book == null){
            return "Cannot find Book " +id;
        }else{
            bookRepository.delete(book);
            return "Book "+id+ " has been deleted !";
        }
    }

    @Override
    public Book updateBook(Long id, Book book) {

        Calendar cal = Calendar.getInstance();
        book.setUpdatedAt(cal.getTime());

        Book bookExisted = bookRepository.findById(id).get();
        bookExisted.setTitle(book.getTitle());
        bookExisted.setPublisher(book.getPublisher());
        bookExisted.setThumbnail(book.getThumbnail());
        bookExisted.setLanguage(book.getLanguage());
        bookExisted.setDescription(book.getDescription());
        bookExisted.setAuthor(book.getAuthor());
        bookExisted.setAmount(book.getAmount());
        bookExisted.setPrice(book.getPrice());
        bookExisted.setBorrowPrice(book.getBorrowPrice());
        bookExisted.setStatus(book.getStatus());
        bookExisted.setUpdatedAt(book.getUpdatedAt());
        bookExisted.setPublishedAt(book.getPublishedAt());
        bookExisted.setCreatedAt(bookExisted.getCreatedAt());
        bookExisted.setCategory(book.getCategory());
        bookRepository.save(bookExisted);
        return bookExisted;
    }

}

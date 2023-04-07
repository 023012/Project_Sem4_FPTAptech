package com.library.controller;

import com.library.entity.Book;
import com.library.entity.Category;
import com.library.repository.BookRepository;
import com.library.repository.CategoryRepository;
import com.library.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
//@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BookController {
    private final BookRepository bookRepository;

    private final BookService bookService;
    private final CategoryRepository categoryRepository;

//    Admin Backend

    //    GetAllBooks
    @GetMapping("/books")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    //    SearchBookByKeyword
    @GetMapping("/books/search")
    public List<Book> getAllBooksByKeyword(@RequestParam("keyword") String keyword) {
        return bookService.getAllBookByKeyword(keyword);
    }

    //    GetBookById
    @GetMapping("/book/{id}")
    public ResponseEntity<?> getBookByID(@PathVariable Long id) {
        if (bookRepository.findById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book with id " + id + " is not existed !");
        } else {
            return ResponseEntity.ok().body(bookRepository.findById(id));
        }
    }

    //    GetBookByCategory
    @GetMapping("/books/category/{cateId}")
    public ResponseEntity<?> getBookByCateID(@PathVariable Long cateId) {
        if (bookService.getAllBookByCategoryID(cateId) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("List books is empty!");
        } else {
            return ResponseEntity.ok().body(bookService.getAllBookByCategoryID(cateId));
        }
    }

    //    SearchBookByCategoryIdOrKeyword
    @GetMapping("/books/cateID-search")
    public ResponseEntity<?> getBookByCateIDAndKeyword(@RequestParam("cateID") Long cateID,
                                                       @RequestParam("keyword") String keyword) {
        if (bookService.getAllBookByCateIDAndKeyword(cateID, keyword) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("List books is empty!");
        } else {
            return ResponseEntity.ok().body(bookService.getAllBookByCateIDAndKeyword(cateID, keyword));
        }
    }

    //    SaveBook
    @PostMapping("/books/add")
    public Book createBook(@RequestParam("categoryId") Long categoryId, @RequestBody Book book) {
        Category categoryFind = categoryRepository.findById(categoryId).get();
        book.setCategory(categoryFind);
        System.out.println(book);
        return bookService.createBook(book);
    }

    //    RemoveBookById
    @DeleteMapping("/books/delete/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.deleteBook(id));
    }

    //    UpdateBookById
    @PutMapping("/books/save/{id}")
    public ResponseEntity<Book> updateBook(@RequestParam("categoryId") Long categoryId, @PathVariable Long id,
                                           @RequestBody Book book) {
        Category categoryFind = categoryRepository.findById(categoryId).get();
        book.setCategory(categoryFind);
        book = bookService.updateBook(id, book);
        return ResponseEntity.ok(book);
    }

//    Book Controller Client

    @GetMapping("/book-details/{id}")
    public String bookDetails(@PathVariable("id") Long id, Model model) {
        Book book = bookRepository.getById(id);
//        Long categoryId = book.getCategory().getCategoryId();
        model.addAttribute("title", "Single Book");
        model.addAttribute("book", book);
        return "client/single-product";
    }
}
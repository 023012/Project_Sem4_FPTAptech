package com.library.controller;

import com.library.entity.Book;
import com.library.entity.Category;
import com.library.repository.BookRepository;
import com.library.repository.CategoryRepository;
import com.library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {
    private final BookRepository bookRepository;
    private final BookService bookService;
    private final CategoryRepository categoryRepository;

    @GetMapping("/admin/books/search")
    public List<Book> getAllBooksByKeyword(@RequestParam("keyword") String keyword) {
        return bookService.getAllBookByKeyword(keyword);
    }


//    Book Admin Controller

    //    GetBookById(Book details admin)
    @GetMapping("/admin/book/{id}")
    public String getBookById(@PathVariable("id") Long id, Model model) {
        Book book = bookRepository.getById(id);
        model.addAttribute("title", "Book Detail");
        model.addAttribute("book", book);
        return "admin/book-detail";
    }



    //    SaveBook
    @RequestMapping(value = "/admin/book/new")
    public String addForm(Model model) {
        Book book = new Book();
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("book", book);
        model.addAttribute("status",book.getStatus());
        model.addAttribute("categories",categories);
        model.addAttribute("title", "Add new book");
        return "admin/book-add";
    }

    @PostMapping("/admin/books/add")
    public String  createBook(@Validated  @ModelAttribute("book") Book book,
                              BindingResult result) {
        if (result.hasErrors()){
            return "redirect:/book/new";
        }
        bookService.createBook(book);
        return "redirect:/admin/books";
    }

    //    GetBookByCategory
    @GetMapping("/admin/books/category/{cateId}")
    public ResponseEntity<?> getBookByCateID(@PathVariable Long cateId) {
        if (bookService.getAllBookByCategoryID(cateId) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("List books is empty!");
        } else {
            return ResponseEntity.ok().body(bookService.getAllBookByCategoryID(cateId));
        }
    }

    //    SearchBookByCategoryIdOrKeyword
    @GetMapping("/admin/books/cateID-search")
    public ResponseEntity<?> getBookByCateIDAndKeyword(@RequestParam("cateID") Long cateID,
                                                       @RequestParam("keyword") String keyword) {
        if (bookService.getAllBookByCateIDAndKeyword(cateID, keyword) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("List books is empty!");
        } else {
            return ResponseEntity.ok().body(bookService.getAllBookByCateIDAndKeyword(cateID, keyword));
        }
    }


    //    RemoveBookById
    @DeleteMapping("/admin/books/delete/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.deleteBook(id));
    }

    //    UpdateBookById
    @PutMapping("/admin/books/save/{id}")
    public ResponseEntity<Book> updateBook(@RequestParam("categoryId") Long categoryId, @PathVariable Long id,
                                           @RequestBody Book book) {
        Category categoryFind = categoryRepository.findById(categoryId).get();
        book.setCategory(categoryFind);
        book = bookService.updateBook(id, book);
        return ResponseEntity.ok(book);
    }


    //    Book Controller Client

    @GetMapping("books/book-details/{id}")
    public String bookDetails(@PathVariable("id") Long id, Model model) {
        Book book = bookRepository.getById(id);
//        Long categoryId = book.getCategory().getCategoryId();
        model.addAttribute("title", "Single Book");
        model.addAttribute("book", book);
        return "client/single-product";
    }

}

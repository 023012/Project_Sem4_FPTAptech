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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @GetMapping("/admin/books")
    public String book(Model model){
        List<Book> books = bookRepository.findAll();
        model.addAttribute("books", books);
        model.addAttribute("size", books.size());
        model.addAttribute("title", "Books");
        return "admin/book";
    }

    //    GetBookById(Book details admin)
    @GetMapping("/admin/books/{id}")
    public String getBookById(@PathVariable("id") Long id, Model model) {
        Book book = bookRepository.getById(id);
        model.addAttribute("title", "Book Detail");
        model.addAttribute("book", book);
        return "admin/book-detail";
    }



    //    SaveBook
    @GetMapping(value = "/admin/books/new")
    public String addForm(Model model) {
        Book book = new Book();
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories",categories);
        model.addAttribute("title", "Add new book");
        model.addAttribute("book", book);
        return "admin/book-add";
    }

    @PostMapping("/admin/books/add")
    public String  createBook(@ModelAttribute("book") Book book,
                              BindingResult result) {
        if (result.hasErrors()){
            return "admin/book-add";
        }
        bookService.createBook(book);
        return "redirect:/admin/books";
    }


    //    UpdateBookById
    @GetMapping("/admin/books/edit/{id}")
    public String updateForm(@PathVariable("id") long id, Model model){
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Book Id: " + id));
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories",categories);
        model.addAttribute("title","Update book");
        model.addAttribute("book", book);
        return "admin/book-edit";
    }

    @PostMapping("/admin/books/edit/{id}")
    public String updateBook(@PathVariable("id") long id, @Valid Book book,
                             BindingResult result){
        if(result.hasErrors()){
            book.setId(id);
            return "admin/book-edit";
        }
        bookService.updateBook(id, book);
    return "redirect:/admin/books";
    }

    //    RemoveBookById
    @GetMapping("/admin/books/delete/{id}")
    public String deleteBook(@PathVariable Long id, Model model) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        bookRepository.delete(book);
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


    //    Book Controller Client

    @GetMapping("/client/books")
    public String shop(Model model) {
        List<Book> books = bookRepository.findAll();
        model.addAttribute("title", "Books");
        model.addAttribute("books", books);
        return "client/book-list";
    }

    @GetMapping("books/book-details/{id}")
    public String bookDetails(@PathVariable("id") Long id, Model model) {
        Book book = bookRepository.getById(id);
//        Long categoryId = book.getCategory().getCategoryId();
        model.addAttribute("title", "Single Book");
        model.addAttribute("book", book);
        return "client/single-product";
    }

}

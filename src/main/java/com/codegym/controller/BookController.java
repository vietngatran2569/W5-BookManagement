package com.codegym.controller;

import com.codegym.model.Book;
import com.codegym.model.Category;
import com.codegym.service.BookService;
import com.codegym.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class BookController {
    @Autowired
    BookService bookService;
    @Autowired
    CategoryService categoryService;

    @ModelAttribute("categories")
    public Iterable<Category> categories(){
        return categoryService.findAll();
    }
    @GetMapping("/book")
    public ModelAndView listEmplyee(@RequestParam("s") Optional<String> s, @PageableDefault(size = 10) Pageable pageable){
        Page<Book> books;;
        if (s.isPresent()){
            books=bookService.findAllByNameContaining(s.get(),pageable);
        }else {
            books=bookService.findAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("/book/list");
        modelAndView.addObject("books", books);
//        if (lang.isPresent()) {
//            modelAndView.addObject("lang", lang.get());
//        } else {
//            modelAndView.addObject("lang", "");
//        }
        return modelAndView;
    }
    @GetMapping("/create-book")
    public ModelAndView showCreateForm(){
        ModelAndView modelAndView=new ModelAndView("/book/create");
        modelAndView.addObject("book", new Book());
        return modelAndView;
    }
    @PostMapping("/create-book")
    public ModelAndView saveCustomer(@ModelAttribute("book") Book book){
        bookService.save(book);
        ModelAndView modelAndView = new ModelAndView("/book/create");
        modelAndView.addObject("book", new Book());
        modelAndView.addObject("message", "New book created successfully");
        return modelAndView;
    }

    @GetMapping("/edit-book/{id}")
    public ModelAndView showEditForm(@PathVariable Long id){
        Book book=bookService.findById(id);
        if (book!=null){
            ModelAndView modelAndView=new ModelAndView("/book/edit");
            modelAndView.addObject("book",book);
            return modelAndView;
        }else{
            ModelAndView modelAndView=new ModelAndView("/error.404");
            return modelAndView;
        }
    }
    @PostMapping("/edit-book")
    public ModelAndView updateEm(@ModelAttribute("book") Book book){
        bookService.save(book);
        ModelAndView modelAndView=new ModelAndView("/book/edit");
        modelAndView.addObject("book",book);
        modelAndView.addObject("message","Book Update succeddful");
        return modelAndView;
    }
    @GetMapping("/delete-book/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id){
        Book book = bookService.findById(id);
        if(book != null) {
            ModelAndView modelAndView = new ModelAndView("/book/delete");
            modelAndView.addObject("book", book);
            return modelAndView;

        }else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @PostMapping("/delete-book")
    public String deleteEm(@ModelAttribute("book") Book book){
        bookService.remove(book.getId());
        return "redirect:book";
    }
    @GetMapping("/searchByCategory")
    public ModelAndView getBookByCategory(@RequestParam("search") String search, Pageable pageable){
        Category category = categoryService.findById(Long.parseLong(search));
        Page<Book> books = bookService.findAllByCategory(pageable,category);
        ModelAndView modelAndView = new ModelAndView("/book/list");
        modelAndView.addObject("books",books);
        modelAndView.addObject("search",search);
        return modelAndView;
    }

    @GetMapping("/sortByPriceAsc")
    public ModelAndView getBookSortByPriceAsc(Pageable pageable){
        Page<Book> books = bookService.findAllByOrderByPriceAsc(pageable);
        ModelAndView modelAndView = new ModelAndView("/book/list");
        modelAndView.addObject("books",books);
        return modelAndView;
    }

    @GetMapping("/sortByPriceDesc")
    public ModelAndView getBookSortByPriceDesc(Pageable pageable){
        Page<Book> books = bookService.findAllByOrderByPriceDesc(pageable);
        ModelAndView modelAndView = new ModelAndView("/book/list");
        modelAndView.addObject("books",books);
        return modelAndView;
    }


}

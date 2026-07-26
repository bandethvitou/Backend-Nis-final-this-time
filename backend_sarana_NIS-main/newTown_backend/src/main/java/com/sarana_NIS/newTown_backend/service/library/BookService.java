package com.sarana_NIS.newTown_backend.service.library;

import com.sarana_NIS.newTown_backend.dto.request.library.BookRequest;
import com.sarana_NIS.newTown_backend.dto.request.library.BookSearchRequest;
import com.sarana_NIS.newTown_backend.dto.response.library.BookResponse;
import com.sarana_NIS.newTown_backend.dto.response.library.BookDetailResponse;
import com.sarana_NIS.newTown_backend.dto.response.common.PageResponse;
import com.sarana_NIS.newTown_backend.mapper.library.BookMapper;
import com.sarana_NIS.newTown_backend.model.entity.library.Book;
import com.sarana_NIS.newTown_backend.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private static final Logger log = LoggerFactory.getLogger(BookService.class);

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookService(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Transactional
    public BookResponse createBook(BookRequest request) {
        if (request.getIsbn() != null && bookRepository.findByIsbn(request.getIsbn()).isPresent()) {
            throw new RuntimeException("Book with ISBN already exists: " + request.getIsbn());
        }

        Book book = bookMapper.toEntity(request);
        book.setTotalCopies(request.getQuantity());
        book.setAvailableCopies(request.getQuantity());
        // ✅ Fix: Remove setStatus() - Book entity doesn't have this field
        // Status is derived from availableCopies in the mapper

        Book saved = bookRepository.save(book);
        log.info("Book created: {}", saved.getTitle());
        return bookMapper.toResponse(saved);
    }

    @Transactional
    public BookResponse updateBook(Long bookId, BookRequest request) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        bookMapper.updateEntity(request, book);

        if (request.getQuantity() != null) {
            int oldQuantity = book.getTotalCopies() != null ? book.getTotalCopies() : 0;
            int newQuantity = request.getQuantity();
            int difference = newQuantity - oldQuantity;
            book.setTotalCopies(newQuantity);
            book.setAvailableCopies(book.getAvailableCopies() + difference);
        }

        Book saved = bookRepository.save(book);
        log.info("Book updated: {}", saved.getTitle());
        return bookMapper.toResponse(saved);
    }

    public BookResponse getBookById(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        return bookMapper.toResponse(book);
    }

    public BookDetailResponse getBookDetail(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        return bookMapper.toDetailResponse(book);
    }

    public PageResponse<BookResponse> searchBooks(BookSearchRequest request) {
        PageRequest pageRequest = PageRequest.of(
                request.getPage(),
                request.getSize(),
                Sort.by(request.getSortDirection().equals("ASC") ?
                                Sort.Direction.ASC : Sort.Direction.DESC,
                        request.getSortBy())
        );

        Page<Book> page;
        if (request.getKeyword() != null && !request.getKeyword().isEmpty()) {
            page = bookRepository.searchBooks(request.getKeyword(), pageRequest);
        } else {
            page = bookRepository.findAll(pageRequest);
        }

        List<BookResponse> content = page.getContent().stream()
                .map(bookMapper::toResponse)
                .collect(Collectors.toList());

        return new PageResponse<>(content, page.getNumber(), page.getSize(),
                page.getTotalElements(), page.getTotalPages());
    }

    public List<BookResponse> getAvailableBooks() {
        List<Book> books = bookRepository.findAvailableBooks();
        return books.stream().map(bookMapper::toResponse).collect(Collectors.toList());
    }

    @Transactional
    public void deleteBook(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        bookRepository.delete(book);
        log.info("Book deleted: {}", book.getTitle());
    }
}
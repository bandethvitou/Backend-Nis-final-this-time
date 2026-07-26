package com.sarana_NIS.newTown_backend.controller.library;

import com.sarana_NIS.newTown_backend.dto.request.library.BookRequest;
import com.sarana_NIS.newTown_backend.dto.request.library.BookSearchRequest;
import com.sarana_NIS.newTown_backend.dto.response.common.ApiResponse;
import com.sarana_NIS.newTown_backend.dto.response.common.PageResponse;
import com.sarana_NIS.newTown_backend.dto.response.library.BookResponse;
import com.sarana_NIS.newTown_backend.dto.response.library.BookDetailResponse;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresPermission;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresRole;
import com.sarana_NIS.newTown_backend.service.library.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/library/books")
@Tag(name = "Library Book Management", description = "Book management APIs")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    @RequiresRole({"LIBRARIAN", "MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @RequiresPermission("BOOK_CREATE")
    @Operation(summary = "Create a new book")
    public ResponseEntity<ApiResponse<BookResponse>> createBook(
            @Valid @RequestBody BookRequest request) {
        System.out.println("Creating new book: " + request.getTitle());
        BookResponse response = bookService.createBook(request);
        return ResponseEntity.ok(ApiResponse.success("Book created successfully", response));
    }

    @GetMapping("/{bookId}")
    @RequiresRole({"LIBRARIAN", "TEACHER", "STUDENT", "PARENT"})
    @Operation(summary = "Get book by ID")
    public ResponseEntity<ApiResponse<BookResponse>> getBookById(
            @PathVariable Long bookId) {
        System.out.println("Fetching book: " + bookId);
        BookResponse response = bookService.getBookById(bookId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/{bookId}/detail")
    @RequiresRole({"LIBRARIAN", "TEACHER", "STUDENT", "PARENT"})
    @Operation(summary = "Get book detail with history")
    public ResponseEntity<ApiResponse<BookDetailResponse>> getBookDetail(
            @PathVariable Long bookId) {
        System.out.println("Fetching book detail: " + bookId);
        BookDetailResponse response = bookService.getBookDetail(bookId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/search")
    @RequiresRole({"LIBRARIAN", "TEACHER", "STUDENT", "PARENT"})
    @Operation(summary = "Search books")
    public ResponseEntity<ApiResponse<PageResponse<BookResponse>>> searchBooks(
            @Valid @RequestBody BookSearchRequest request) {
        System.out.println("Searching books with keyword: " + request.getKeyword());
        PageResponse<BookResponse> response = bookService.searchBooks(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/available")
    @RequiresRole({"LIBRARIAN", "TEACHER", "STUDENT", "PARENT"})
    @Operation(summary = "Get available books")
    public ResponseEntity<ApiResponse<List<BookResponse>>> getAvailableBooks() {
        System.out.println("Fetching available books");
        List<BookResponse> books = bookService.getAvailableBooks();
        return ResponseEntity.ok(ApiResponse.success(books));
    }

    @PutMapping("/{bookId}")
    @RequiresRole({"LIBRARIAN", "MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @RequiresPermission("BOOK_UPDATE")
    @Operation(summary = "Update book")
    public ResponseEntity<ApiResponse<BookResponse>> updateBook(
            @PathVariable Long bookId,
            @Valid @RequestBody BookRequest request) {
        System.out.println("Updating book: " + bookId);
        BookResponse response = bookService.updateBook(bookId, request);
        return ResponseEntity.ok(ApiResponse.success("Book updated successfully", response));
    }

    @DeleteMapping("/{bookId}")
    @RequiresRole({"LIBRARIAN", "MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @RequiresPermission("BOOK_DELETE")
    @Operation(summary = "Delete book")
    public ResponseEntity<ApiResponse<Void>> deleteBook(
            @PathVariable Long bookId) {
        System.out.println("Deleting book: " + bookId);
        bookService.deleteBook(bookId);
        return ResponseEntity.ok(ApiResponse.success("Book deleted successfully", null));
    }
}
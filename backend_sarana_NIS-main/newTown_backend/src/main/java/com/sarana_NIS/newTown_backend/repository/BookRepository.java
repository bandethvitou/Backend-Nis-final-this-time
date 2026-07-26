package com.sarana_NIS.newTown_backend.repository;

import com.sarana_NIS.newTown_backend.model.entity.library.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByIsbn(String isbn);

    List<Book> findByGenre(String genre);

    Page<Book> findByGenre(String genre, Pageable pageable);

    @Query("SELECT b FROM Book b WHERE b.availableCopies > 0")
    List<Book> findAvailableBooks();

    @Query("SELECT b FROM Book b WHERE b.availableCopies > 0 AND b.isActive = true")
    List<Book> findAvailableForCheckout();

    @Query("SELECT b FROM Book b WHERE b.title LIKE CONCAT('%', :keyword, '%') OR " +
            "b.author LIKE CONCAT('%', :keyword, '%') OR " +
            "b.isbn LIKE CONCAT('%', :keyword, '%')")
    Page<Book> searchBooks(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT b FROM Book b WHERE b.genre = :genre AND b.availableCopies > 0")
    List<Book> findAvailableByGenre(@Param("genre") String genre);

    // ❌ DELETE THIS METHOD - Book doesn't have status field
    // @Query("SELECT COUNT(b) FROM Book b WHERE b.status = 'CHECKED_OUT'")
    // Long countCheckedOutBooks();

    @Query("SELECT COUNT(b) FROM Book b WHERE b.availableCopies > 0")
    Long countAvailableBooks();

    @Query("SELECT b.genre, COUNT(b) FROM Book b GROUP BY b.genre")
    List<Object[]> countBooksByGenre();

    @Query("SELECT b FROM Book b WHERE b.totalCopies > b.availableCopies")
    List<Book> findBooksWithCheckedOutCopies();

    @Query("SELECT b FROM Book b WHERE b.totalCopies = 0")
    List<Book> findOutOfStockBooks();
}
package com.librarymanagement.repository;

import com.librarymanagement.enums.BookCategory;
import com.librarymanagement.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByIsbn(String isbn);

    List<Book> findByAvailableTrue();

    List<Book> findByCategory(BookCategory category);

    boolean existsByIsbn(String isbn);
}
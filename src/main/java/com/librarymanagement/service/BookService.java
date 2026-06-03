package com.librarymanagement.service;

import com.librarymanagement.dto.BookDTO;
import com.librarymanagement.exception.DuplicateResourceException;
import com.librarymanagement.exception.ResourceNotFoundException;
import com.librarymanagement.mapper.BookMapper;
import com.librarymanagement.model.Book;
import com.librarymanagement.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper     bookMapper;

    @Transactional
    public BookDTO.Response create(BookDTO.Request request) {
        if (bookRepository.existsByIsbn(request.isbn())) {
            throw new DuplicateResourceException(
                    "Book already exists with ISBN: " + request.isbn());
        }
        Book book = bookMapper.toEntity(request);
        book.setAvailable(true);
        return bookMapper.toResponse(bookRepository.save(book));
    }

    public List<BookDTO.Response> findAll() {
        return bookRepository.findAll()
                .stream().map(bookMapper::toResponse).toList();
    }

    public BookDTO.Response findById(Long id) {
        return bookRepository.findById(id)
                .map(bookMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Book", id));
    }

    public List<BookDTO.Response> findAvailable() {
        return bookRepository.findByAvailableTrue()
                .stream().map(bookMapper::toResponse).toList();
    }

    @Transactional
    public BookDTO.Response update(Long id, BookDTO.Request request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book", id));

        // Allow same ISBN on update (only block if another book has it)
        bookRepository.findByIsbn(request.isbn())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new DuplicateResourceException(
                            "ISBN already in use: " + request.isbn());
                });

        bookMapper.updateEntity(request, book);
        return bookMapper.toResponse(bookRepository.save(book));
    }

    @Transactional
    public void delete(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Book", id);
        }
        bookRepository.deleteById(id);
    }
}
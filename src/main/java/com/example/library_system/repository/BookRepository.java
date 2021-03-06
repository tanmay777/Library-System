package com.example.library_system.repository;

import com.example.library_system.model.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<BookEntity,Integer> {

  /**
   * Finds the book by the id provided.
   * @param id book's id
   * @return BookEntity
   */
  Optional<BookEntity> findByBookId(Integer id);

  /**
   * Returns all books.
   * @return books
   */
  List<BookEntity> findAll();

  /**
   * Saves the bookEntity.
   * @param bookEntity The bookEntity to be saved.
   * @return Saved BookEntity.
   */
  BookEntity save(BookEntity bookEntity);

  /**
   * Delete the book by the given Id.
   * @param id BookEntity's Id.
   * @return Deleted BookEntity.
   */
  void deleteByBookId(Integer id);
}

package com.example.library_system.service.Implementation;

import com.example.library_system.dto.BookDto;
import com.example.library_system.exception.BookException;
import com.example.library_system.mapper.BookDtoBookEntityMapper;
import com.example.library_system.model.BookEntity;
import com.example.library_system.repository.BookRepository;
import com.example.library_system.service.BookService;
import com.example.library_system.util.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
public class BookServiceImplementation implements BookService {

  @Autowired
  BookRepository bookRepository;

  @Autowired
  BookDtoBookEntityMapper bookDtoBookEntityMapper;

  /**
   * Gets all the books.
   *
   * @return All Books
   */
  @Override
  public List<BookDto> getAllBooks() {
    try {
      List<BookEntity> bookEntityArrayList = bookRepository.findAll();
      if (bookEntityArrayList.isEmpty()) {
        LogUtils.getInfoLogger().info("No Books found");
        return null;
      } else {
        List<BookDto> bookDtoArrayList = bookEntityArrayList.stream()
            .map(bookEntity ->
                bookDtoBookEntityMapper.bookEntityToBookDto(bookEntity))
            .collect(Collectors.toList());
        LogUtils.getInfoLogger().info("Books Found: {}", bookDtoArrayList.toString());
        return bookDtoArrayList;
      }
    }catch (Exception exception){
      throw new BookException(exception.getMessage());
    }
  }

  /**
   * Get the book according to bookId.
   *
   * @param bookId BookEntity Id
   * @return book according to bookId
   */
  @Override
  public Optional<BookDto> getBook(Integer bookId) {
    try {
      Optional<BookEntity> bookEntity = bookRepository.findByBookId(bookId);
      if (bookEntity.isPresent()) {
        LogUtils.getInfoLogger().info("Found the book: {}", bookEntity.get());
        return Optional.of(bookDtoBookEntityMapper.bookEntityToBookDto(bookEntity.get()));
      } else {
        LogUtils.getInfoLogger().info("Book not found");
        return Optional.empty();
      }
    }catch (Exception exception){
      throw new BookException(exception.getMessage());
    }
  }

  /**
   * Puts the book according to book id.
   *
   * @param bookId  id of the book which has to updated
   * @param bookDto updated bookDto
   * @return
   */
  @Override
  public Optional<BookDto> updateBook(Integer bookId, BookDto bookDto) {
    try {
      Optional<BookEntity> bookEntity = bookRepository.findByBookId(bookId);
      if (bookEntity.isPresent()) {
        BookEntity updatedBookEntity = bookRepository.save(new BookEntity(bookId,
            bookDto.getBookName(), bookDto.getBookAuthor()));
        LogUtils.getInfoLogger().info("Book Updated: {}", updatedBookEntity.toString());
        return Optional.of(bookDtoBookEntityMapper.bookEntityToBookDto(updatedBookEntity));
      } else {
        LogUtils.getInfoLogger().info("Book Not updated");
        return Optional.empty();
      }
    }catch (Exception exception){
      throw new BookException(exception.getMessage());
    }
  }

  /**
   * Add the book.
   *
   * @return added book.
   */
  @Override
  public Optional<BookDto> addBook(BookDto bookDto) {
    try {
      BookEntity addedBookEntity =
          bookRepository.save(bookDtoBookEntityMapper.bookDtoToBookEntity(bookDto));
      LogUtils.getInfoLogger().info("Book Added: {}", addedBookEntity.toString());
      return Optional.of(bookDtoBookEntityMapper.bookEntityToBookDto(addedBookEntity));
    }catch (Exception exception){
      throw new BookException(exception.getMessage());
    }
  }

  /**
   * Delete the book based on the id.
   *
   * @param bookId book's id to be deleted.
   * @return Delete BookEntity
   */
  @Override
  public Optional<BookDto> deleteBook(Integer bookId) {
    try {
      Optional<BookEntity> bookEntity = bookRepository.findByBookId(bookId);
      if (bookEntity.isPresent()) {
        bookRepository.deleteByBookId(bookId);
        LogUtils.getInfoLogger().info("Book Deleted: {}",bookEntity.get().toString());
        return Optional.of(bookDtoBookEntityMapper.bookEntityToBookDto(bookEntity.get()));
      } else {
        LogUtils.getInfoLogger().info("Book not Deleted");
        return Optional.empty();
      }
    }catch (Exception exception){
      throw new BookException(exception.getMessage());
    }
  }
}

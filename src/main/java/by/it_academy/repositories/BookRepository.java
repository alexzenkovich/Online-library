package by.it_academy.repositories;

import by.it_academy.model.books.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    Book findBookById(Long id);

    @Query("select b from Book b left join fetch b.basketCells where b.id = ?1")
    Book findBookWithBasketCellsById(Long id);

    Page<Book> findAll(Pageable pageable);
}

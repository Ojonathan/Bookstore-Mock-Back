package be.businesstraining.rest;

import be.businesstraining.domain.Book;
import be.businesstraining.repository.IBooksRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
@CrossOrigin
public class BooksRestController {

    private IBooksRepository booksRepository;

    // The @Autowired decoration is not required for constructor injection
    public BooksRestController(IBooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    @GetMapping
    public List<Book> findAll() {
        return  booksRepository.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Book> findById(@PathVariable Long id) {
        Optional<Book> book = booksRepository.findById(id);
        return  (book.isPresent()) ?
                new ResponseEntity<>(book.get(), HttpStatus.OK):
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping (params = "filter")
    public List<Book> findBooksFilteredByTitleOrAuthors(@PathParam(value = "filter") String filter) {
        return  booksRepository.
                findAllByTitleContainingIgnoreCaseOrAuthorsContainingIgnoreCase(filter,filter);
    }
}

package com.example.bookstorebackend.rest;

import com.example.bookstorebackend.model.Book;
import com.example.bookstorebackend.repository.BookRepository;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/books")
public class BookEndPoint {

    public Book getBook(Long id) {
        return bookRepository.find(id);
    }

    public Book createBook(Book book) {
        return bookRepository.create(book);
    }

    public void deleteBook(Long id) {
        bookRepository.delete(id);
    }

    @GET
    @Produces(APPLICATION_JSON)
    public Response getBooks() {
        List<Book> books = bookRepository.findAll();

        if(books.size() == 0)
            return Response.noContent().build();
        return Response.ok(books).build();
    }

    @GET
    @Path("/count")
    public Response countBooks() {
        Long nbOfBooks = bookRepository.countAll();

        if (nbOfBooks == 0)
            return Response.noContent().build();
        return Response.ok(nbOfBooks).build();
    }

    @Inject
    private BookRepository bookRepository;
}
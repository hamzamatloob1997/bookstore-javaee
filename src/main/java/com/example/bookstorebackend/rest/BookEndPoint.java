package com.example.bookstorebackend.rest;

import com.example.bookstorebackend.model.Book;
import com.example.bookstorebackend.repository.BookRepository;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import javax.validation.constraints.Min;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/books")
public class BookEndPoint {

    @GET
    @Path("/{id: \\d+}")
    public Book getBook(@PathParam("id") @Min(1) Long id) {
        return bookRepository.find(id);
    }

    @POST
    @Consumes(APPLICATION_JSON)
    public Response createBook(Book book, @Context UriInfo uriInfo) {
        book = bookRepository.create(book);
        URI createdUri = uriInfo.getBaseUriBuilder().path(book.getId().toString()).build();
        return Response.created(createdUri).build();
    }

    @DELETE
    @Path("/{id: \\d+}")
    public Response deleteBook(@PathParam("id") @Min(1) Long id) {
        bookRepository.delete(id);
        return Response.noContent().build();
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
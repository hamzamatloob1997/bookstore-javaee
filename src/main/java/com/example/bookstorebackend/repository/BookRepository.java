package com.example.bookstorebackend.repository;

import com.example.bookstorebackend.model.Book;
import com.example.bookstorebackend.util.NumberGenerator;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;

@Transactional(Transactional.TxType.SUPPORTS)
public class BookRepository {

    @PersistenceContext(unitName = "bookStorePU")
    private EntityManager em;

    @Inject
    private NumberGenerator numberGenerator;

    public Book find(@NotNull Long id) {
        return em.find(Book.class, id);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public Book create(@NotNull Book book) {
        book.setIsbn(Float.valueOf(numberGenerator.generateNumber()));
        em.persist(book);
        return book;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void delete(@NotNull Long id) {
        em.remove(em.getReference(Book.class, id));
    }

    public List<Book> findAll() {
        TypedQuery<Book> query = em.createQuery("SELECT b from Book b order by b.title", Book.class);
        return query.getResultList();
    }

    public  Long countAll () {
        TypedQuery<Long> query = em.createQuery("SELECT COUNT (b) from Book b", Long.class);
        return query.getSingleResult();
    }
}
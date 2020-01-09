package ua.com.parkhub.persistence;

import java.util.List;
import java.util.Optional;

public interface IElementDAO<E> {
    Optional<E> addElement(E element);
    void updateElement(E element);
    Optional<E> findElementById(long id);
    List<E> findAll();
    void deleteElement(E element);
    <F> Optional<E> findOneByFieldEqual(String fieldName, F fieldValue);
    <F> List<E> findManyByFieldEqual(String fieldName, F fieldValue);
}

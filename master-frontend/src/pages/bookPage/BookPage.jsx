import React from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import useCart from '../../hooks/useCart';
import booksData from '../../assets/books.json';
import './BookPage.scss';

const BookPage = () => {
  const { id } = useParams();
  const navigate = useNavigate()
  const book = booksData.find((b) => b.id === id);
  const { addToCart, cart } = useCart();

  if (!book) {
    return <p className="book-page__not-found">Libro no encontrado.</p>;
  }

  const isInCart = cart.some((item) => item.id === book.id);
  const handleAdd = () => {
    if (!isInCart) {
      addToCart(book);
      navigate('/checkout');
    }
  };
  return (
    <div className="book-page">
      <div className="book-page__image-wrapper">
        <img src={book.cover} alt={book.title} className="book-page__image" />
      </div>
      <div className="book-page__details">
        <h2 className="book-page__title">{book.title}</h2>
        <p className="book-page__author">Por {book.author}</p>
        <p className="book-page__price">Precio: ${book.price.toFixed(2)}</p>
        <button
          className="book-page__add-button"
          onClick={handleAdd}
          disabled={isInCart}
        >
          {isInCart ? 'En el carrito' : 'Añadir al carrito'}
        </button>
      </div>
    </div>
  );
};

export default BookPage;

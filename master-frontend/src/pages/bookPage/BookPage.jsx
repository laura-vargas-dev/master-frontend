import { useParams } from "react-router-dom";
import booksData from "../../assets/books.json";
import "./BookPage.scss";
import { useCartContext } from "../../components/CartContext";

const BookPage = () => {
  const { id } = useParams();
  const book = booksData.find((b) => b.id === id);
  const { addToCart, cart } = useCartContext();

  if (!book) {
    return <p className="book-page__not-found">Libro no encontrado.</p>;
  }

  const isInCart = cart.some((item) => item.id === book.id);
  const handleAdd = () => {
    if (!isInCart) addToCart(book);
  };

  return (
    <div className="book-page">
      <div className="book-page__image-wrapper">
        <img
          src={book.cover}
          alt={book.title}
          className="book-page__image"
          onError={({ currentTarget }) => {
            currentTarget.onerror = null;
            currentTarget.src = "/covers/cover-general-book.png";
          }}
        />
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
          {isInCart ? "En el carrito" : "Añadir al carrito"}
        </button>
      </div>
    </div>
  );
};

export default BookPage;

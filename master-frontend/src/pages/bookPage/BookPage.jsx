import { useParams, useNavigate } from "react-router-dom";
import "./BookPage.scss";
import { useCartContext } from "../../components/CartContext";
import { useEffect, useState } from "react";
import axios from "axios";

const BookPage = () => {
  const url = import.meta.env.VITE_API_URL || "http://localhost:8088/api/books";
  const { id } = useParams();
  const navigate = useNavigate();
  const [book, setBook] = useState(null);
  const { addToCart, cart } = useCartContext();

  useEffect(() => {
    axios
      .get(`${url}/${id}`)
      .then((response) => {
        console.log(response.data);
        setBook(response.data);
      })
      .catch((error) => {
        console.error("Error fetching the book", error);
      });
  }, []);

  if (!book) {
    return <p className="book-page__not-found">Libro no encontrado.</p>;
  }

  const isInCart = cart.some((item) => item.id === book.id);
  const handleAdd = () => {
    if (!isInCart) {
      addToCart(book);
      navigate("/checkout");
    }
  };

  return (
    <div className="book-page">
      <div className="book-page__image-wrapper">
        <img
          src={book.imgUrl}
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

import React from "react";
import { useNavigate } from "react-router-dom";
import "./BookCard.scss";
import PropTypes from "prop-types";

const BookCard = ({ book }) => {
  const navigate = useNavigate();
  const handleClick = () => navigate(`/book/${book.id}`);  
  return (
    <button className="book-card" onClick={handleClick} type="button">
      <img
        src={book.cover}
        alt={book.title}
        className="book-card__image"
        onError={({ currentTarget }) => {
          currentTarget.onerror = null;
          currentTarget.src = "/covers/cover-general-book.png";
        }}
      />
      <div className="book-card__info">
        <h3 className="book-card__title">{book.title}</h3>
        <p className="book-card__author">{book.author}</p>
        <p className="book-card__price">${book.price.toFixed(2)}</p>
      </div>
    </button>
  );
};
BookCard.propTypes = {
  book: PropTypes.shape({
    id: PropTypes.oneOfType([PropTypes.string, PropTypes.number]).isRequired,
    cover: PropTypes.string.isRequired,
    title: PropTypes.string.isRequired,
    author: PropTypes.string.isRequired,
    price: PropTypes.number.isRequired,
  }).isRequired,
};

export default BookCard;

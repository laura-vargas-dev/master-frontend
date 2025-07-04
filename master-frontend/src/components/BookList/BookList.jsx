import React from "react";
import PropTypes from "prop-types";
import "./BookList.scss";
import BookCard from "../BookCard/BookCard";

const BookList = ({ books, loadMore }) => (
  <div className="books">
    <div className="book-list">
      {books.map((book, index) => (
        <BookCard key={book.title + index} book={book} />
      ))}
    </div>

    <div className="book-list__actions">
      <button onClick={loadMore}>Ver más</button>
    </div>
  </div>
);

BookList.propTypes = {
  books: PropTypes.array.isRequired,
};

export default BookList;

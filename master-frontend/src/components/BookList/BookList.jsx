import React from 'react';
import PropTypes from 'prop-types';
import './BookList.scss';
import BookCard from '../BookCard/BookCard';
const BookList = ({ books }) => (
  <div className="book-list">
    {books.map((book) => (
      <BookCard key={book.id} book={book} />
    ))}
  </div>
);

BookList.propTypes = {
  books: PropTypes.array.isRequired,
};

export default BookList;

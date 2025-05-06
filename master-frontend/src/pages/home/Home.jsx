import React, { useState, useEffect } from 'react';
import SearchBar from '../../components/SearchBar/SearchBar';
import BookList from '../../components/BookList/BookList';
import booksData from '../../assets/books.json';
import './Home.scss';

const Home = () => {
  const [books, setBooks] = useState([]);
  const [filtered, setFiltered] = useState([]);

  useEffect(() => {
    setBooks(booksData);
    setFiltered(booksData);
  }, []);

  const handleSearch = (term) => {
    const lower = term.toLowerCase();
    setFiltered(
      books.filter((book) =>
        book.title.toLowerCase().includes(lower)
      )
    );
  };

  return (
    <main className="home">
      <SearchBar onSearch={handleSearch} />
      <div className="home__list">
        <BookList books={filtered} />
      </div>
    </main>
  );
};

export default Home;
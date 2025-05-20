import React, { useState, useEffect } from 'react';
import BookList from '../../components/BookList/BookList';
import booksData from '../../assets/books.json';
import './Home.scss';

const Home = () => {
  const [filtered, setFiltered] = useState([]);

  useEffect(() => {
    setFiltered(booksData);
  }, []);


  return (
    <main className="home">
      <div className="home__list">
        <BookList books={filtered} />
      </div>
    </main>
  );
};

export default Home;
import React, { useState } from 'react';
import PropTypes from 'prop-types';
import './SearchBar.scss';

const SearchBar = ({ onSearch, placeholder = 'Buscar libro...' }) => {
  const [term, setTerm] = useState('');

  const handleChange = (e) => {
    const value = e.target.value;
    setTerm(value);
    if (onSearch) onSearch(value);
  };

  return (
    <div className="search-bar">
      <input
        type="text"
        className="search-bar__input"
        placeholder={placeholder}
        value={term}
        onChange={handleChange}
        aria-label="Search books by title"
      />
    </div>
  );
};

SearchBar.propTypes = {
  onSearch: PropTypes.func,
  placeholder: PropTypes.string,
};

export default SearchBar;
import React, { useState } from 'react';
import PropTypes from 'prop-types';
import './SearchBar.scss';
import {useNavigate} from "react-router-dom";

const SearchBar = ({ books = [], onSearch, placeholder = '¿Qué estás buscando hoy?' }) => {
    const [term, setTerm] = useState('');
    const [suggestions, setSuggestions] = useState([]);
    const [showSuggestions, setShowSuggestions] = useState(false);
    const navigate = useNavigate();


    const handleChange = (e) => {
        const value = e.target.value;
        setTerm(value);

        if (value.length > 0) {
            const filtered = books.filter(book =>
                book.title.toLowerCase().includes(value.toLowerCase()) ||
                book.author.toLowerCase().includes(value.toLowerCase())
            );
            setSuggestions(filtered);
            setShowSuggestions(true);
        } else {
            setSuggestions([]);
            setShowSuggestions(false);
        }
    };

    const handleSelectSuggestion = (book) => {
        navigate(`/book/${book.id}`);
        setShowSuggestions(false);
    };



    const handleSubmit = (e) => {
        e.preventDefault();
        onSearch(term);
        setShowSuggestions(false);
    };

    return (
        <form className="search-bar" onSubmit={handleSubmit} role="search" autoComplete="off">
            <input
                type="text"
                className="search-bar__input"
                placeholder={placeholder}
                value={term}
                onChange={handleChange}
                onBlur={() => setTimeout(() => setShowSuggestions(false), 150)} // timeout para click en sugerencia
                onFocus={() => term.length > 0 && setShowSuggestions(true)}
                aria-label="Buscar libros"
            />


            {showSuggestions && suggestions.length > 0 && (
                <ul className="search-bar__suggestions" role="listbox">
                    {suggestions.map((book) => (
                        <li
                            key={book.id}
                            className="search-bar__suggestion"
                            onMouseDown={() => handleSelectSuggestion(book)}
                            role="option"
                            tabIndex={0}
                        >
                            <img
                                src={book.cover}
                                alt={`Portada de ${book.title}`}
                                className="search-bar__suggestion-cover"
                                width={30}
                                height={40}
                            />
                            <div className="search-bar__suggestion-text">
                                <strong>{book.title}</strong><br />
                                <small>{book.author}</small>
                            </div>
                        </li>
                    ))}
                </ul>
            )}
        </form>
    );
};

SearchBar.propTypes = {
    books: PropTypes.arrayOf(
        PropTypes.shape({
            id: PropTypes.string.isRequired,
            title: PropTypes.string.isRequired,
            author: PropTypes.string.isRequired,
            cover: PropTypes.string,
        })
    ),
    onSearch: PropTypes.func.isRequired,
    placeholder: PropTypes.string,
};

export default SearchBar;

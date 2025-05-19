import React, {useState} from 'react';
import PropTypes from 'prop-types';
import './SearchBar.scss';

const SearchBar = ({onSearch, placeholder = 'Buscar libro...'}) => {
    const [term, setTerm] = useState('');
    const [showInput, setShowInput] = useState(false);

    const handleChange = (e) => {
        const value = e.target.value;
        setTerm(value);
        if (onSearch) onSearch(value);
    };

    const toggleInput = () => {
        setShowInput(!showInput);
    };

    return (
        <div className="search-bar">
            <button className="search-bar__icon" onClick={toggleInput} aria-label="Toggle search input">
                🔍
            </button>
            {showInput && (
                <input
                    type="text"
                    className="search-bar__input"
                    placeholder={placeholder}
                    value={term}
                    onChange={handleChange}
                />
            )}
        </div>);
};

SearchBar.propTypes = {
    onSearch: PropTypes.func,
    placeholder: PropTypes.string,
};

export default SearchBar;

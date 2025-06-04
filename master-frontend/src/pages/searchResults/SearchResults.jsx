import { useLocation } from "react-router-dom";
import books from "../../assets/books.json";
import './SearchResults.scss';

const SearchResults = () => {
    const location = useLocation();
    const query = new URLSearchParams(location.search).get("q") || "";
    const lowerQuery = query.toLowerCase();

    const filteredBooks = books.filter(
        (book) =>
            book.title.toLowerCase().includes(lowerQuery) ||
            book.author.toLowerCase().includes(lowerQuery)
    );

    return (
        <div className="search-results">
            <h2>Resultados de búsqueda para: "{query}"</h2>

            {filteredBooks.length > 0 ? (
                <ul className="search-results__list">
                    {filteredBooks.map((book) => (
                        <li key={book.id} className="search-results__item">
                            <a href={`/book/${book.id}`}>
                                <img src={book.cover} alt={book.title} />
                                <div>
                                    <strong>{book.title}</strong><br />
                                    <small>{book.author}</small>
                                </div>
                            </a>
                        </li>
                    ))}
                </ul>
            ) : (
                <p className="search-results__no-results">
                    No se encontraron resultados que coincidan con "{query}".
                </p>
            )}
        </div>
    );
};

export default SearchResults;

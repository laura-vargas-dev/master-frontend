import {useNavigate, useLocation} from 'react-router-dom';
import './Header.scss';
import {useCartContext} from '../CartContext';
import books from "../../assets/books.json";
import SearchBar from '../../components/SearchBar/SearchBar';


const Header = () => {
    const navigate = useNavigate();
    const location = useLocation();
    const {cart} = useCartContext();


    const totalAmount = cart.reduce((total, item) => total + item.amount, 0);


    const handleLogoClick = () => navigate('/home');
    const handleCartClick = () => navigate('/checkout');


    const showSearchBar = location.pathname === '/home' || location.pathname === '/search';

    return (
        <header className="header">
            <button type="button" className="header__logo" onClick={handleLogoClick}>
                <h1 className="header__logo-text">Relatos de Papel</h1>
            </button>

            <nav className="header__nav">
                {showSearchBar && (
                    <SearchBar
                        books={books}
                        onSearch={(term) => {
                            if (term.trim() !== '') {
                                navigate(`/search?q=${encodeURIComponent(term.trim())}`);
                            }
                        }}
                    />

                )}

                <button
                    className="header__cart"
                    onClick={handleCartClick}
                    aria-label="Ver carrito"
                >
                    🛒
                    {totalAmount > 0 && (
                        <span className="header__cart__badge">{totalAmount}</span>
                    )}
                </button>
            </nav>

        </header>
    );
};

export default Header;

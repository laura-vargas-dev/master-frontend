import { useNavigate } from 'react-router-dom';
import './Header.scss';
import { useCartContext } from '../CartContext';

const Header = () => {
  const navigate = useNavigate();
  const { cart } = useCartContext();

  const handleLogoClick = () => navigate('/home');
  const handleCartClick = () => navigate('/checkout');

  return (
    <header className="header">
      <button type="button" className="header__logo" onClick={handleLogoClick}>
        <h1 className="header__logo-text">Relatos de Papel</h1>
      </button>
      <nav className="header__nav">
        {cart.length > 0 && <span className="header__cart__badge">{cart.length}</span>}        
        <button
          className="header__cart"
          onClick={handleCartClick}
          aria-label="View cart"
        >
          🛒
        </button>
      </nav>
    </header>
  );
};

export default Header;
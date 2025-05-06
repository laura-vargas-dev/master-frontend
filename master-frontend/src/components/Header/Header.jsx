import React from 'react';
import { useNavigate } from 'react-router-dom';
import './Header.scss';

const Header = () => {
  const navigate = useNavigate();

  const handleLogoClick = () => navigate('/home');
  const handleCartClick = () => navigate('/checkout');

  return (
    <header className="header">
      <button type="button" className="header__logo" onClick={handleLogoClick}>
        <h1 className="header__logo-text">BookStore</h1>
      </button>
      <nav className="header__nav">
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
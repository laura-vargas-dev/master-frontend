import React from 'react';
import PropTypes from 'prop-types';
import './CartItem.scss';

const CartItem = ({ item, onRemove }) => (
  <li className="cart-item">
    <img src={item.cover} alt={item.title} className="cart-item__image" />
    <div className="cart-item__info">
      <h4 className="cart-item__title">{item.title}</h4>
      <span className="cart-item__price">${item.price.toFixed(2)}</span>
    </div>
    <button
      className="cart-item__remove"
      onClick={onRemove}
      aria-label="Eliminar libro"
    >
      ✕
    </button>
  </li>
);

CartItem.propTypes = {
  item: PropTypes.shape({
    cover: PropTypes.string.isRequired,
    title: PropTypes.string.isRequired,
    price: PropTypes.number.isRequired,
  }).isRequired,
  onRemove: PropTypes.func.isRequired,
};
export default CartItem;
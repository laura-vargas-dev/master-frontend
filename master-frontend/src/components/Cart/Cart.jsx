import React from 'react';
import { useNavigate } from 'react-router-dom';
import useCart from '../../hooks/useCart';
import CartItem from '../CartItem/CartItem';
import './Cart.scss';

const Cart = () => {
  const { cart, removeFromCart } = useCart();
  const navigate = useNavigate();

  const total = cart.reduce((sum, item) => sum + item.price, 0).toFixed(2);

  return (
    <div className="cart">
      <h2 className="cart__title">Carrito</h2>
      {cart.length === 0 ? (
        <p className="cart__empty">Tu carrito está vacío.</p>
      ) : (
        <>
          <ul className="cart__list">
            {cart.map((item) => (
              <CartItem
                key={item.id}
                item={item}
                onRemove={() => removeFromCart(item.id)}
              />
            ))}
          </ul>
          <div className="cart__footer">
            <span className="cart__total">Total: ${total}</span>
            <button
              className="cart__checkout-button"
              onClick={() => navigate('/checkout')}
            >
              Checkout
            </button>
          </div>
        </>
      )}
    </div>
  );
};

export default Cart;
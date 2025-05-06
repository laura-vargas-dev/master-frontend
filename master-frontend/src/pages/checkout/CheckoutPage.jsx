import React from 'react';
import { useNavigate } from 'react-router-dom';
import useCart from '../../hooks/useCart';
import './CheckoutPage.scss';

const CheckoutPage = () => {
  const { cart, clearCart } = useCart();
  const navigate = useNavigate();

  const total = cart.reduce((sum, item) => sum + item.price, 0).toFixed(2);

  const handleConfirm = () => {
    alert('Pedido realizado con éxito');
    clearCart();
    navigate('/home');
  };

  if (cart.length === 0) {
    return (
      <div className="checkout">
        <p className="checkout__empty">No tienes libros en el carrito.</p>
      </div>
    );
  }

  return (
    <div className="checkout">
      <h2 className="checkout__title">Resumen de tu pedido</h2>
      <ul className="checkout__list">
        {cart.map((item) => (
          <li key={item.id} className="checkout__item">
            {item.title} - ${item.price.toFixed(2)}
          </li>
        ))}
      </ul>
      <div className="checkout__footer">
        <span className="checkout__total">Total: ${total}</span>
        <button
          className="checkout__confirm-button"
          onClick={handleConfirm}
        >
          Confirmar pago
        </button>
      </div>
    </div>
  );
};

export default CheckoutPage;
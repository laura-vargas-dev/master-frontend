import React from 'react';
import {useNavigate} from 'react-router-dom';
import { useCartContext } from '../../components/CartContext';
import './CheckoutPage.scss';
import { FaTrash } from 'react-icons/fa';
import { FaMinus, FaPlus } from 'react-icons/fa';

const CheckoutPage = () => {
    const {cart, clearCart, removeFromCart, editAmountItem} = useCartContext();
    const navigate = useNavigate();

    const total = cart.reduce((sum, item) => sum + item.price * item.amount, 0).toFixed(2);

    const handleConfirm = () => {
        alert('Pedido realizado con éxito');
        clearCart();
        navigate('/home');
    };

    if (cart.length === 0) {
        return (
            <div className="checkout checkout--empty">
                <div className="checkout__empty-content">
                    <h2 className="checkout__empty-title">Su carrito está vacío</h2>
                    <p className="checkout__empty-message">
                        Para seguir comprando, busque su producto y agréguelo al carrito.
                    </p>
                    <button
                        className="checkout__empty-button"
                        onClick={() => navigate('/home')}
                    >
                        Elegir productos
                    </button>
                </div>
            </div>
        );
    }

    return (
        <div className="checkout">
            <h2 className="checkout__title">Resumen de tu pedido</h2>

            <div className="checkout__content">
                <div className="checkout__left">
                    <div className="checkout__header">
                        <span className="checkout__header-product">Producto</span>
                        <span className="checkout__header-price">Precio</span>
                        <span className="checkout__header-quantity">Cantidad</span>
                        <div className="checkout__header-remove"></div>
                    </div>

                    <div className="checkout__list">
                        {cart.map((item) => (
                            <div key={item.id} className="checkout__item">
                                <div className="checkout__item-product">
                                    <img src={item.imgUrl} alt={item.title} className="checkout__item-image" />
                                    <span className="checkout__item-title">{item.title}</span>
                                </div>

                                <div className="checkout__item-price-wrapper">
                                    <span className="checkout__item-price">${item.price.toFixed(2)}</span>
                                </div>

                                <div className="checkout__item-quantity">
                                    <button
                                        className="quantity-btn"
                                        onClick={() => {
                                            if (item.amount > 1) editAmountItem(item, item.amount - 1);
                                        }}
                                        aria-label={`Disminuir cantidad de ${item.title}`}
                                        disabled={item.amount <= 1}
                                    >
                                        <FaMinus />
                                    </button>

                                    <input
                                        type="tel"
                                        min="1"
                                        value={item.amount}
                                        className="quantity-input"
                                        onChange={(e) => {
                                            let val = parseInt(e.target.value);
                                            if (isNaN(val) || val < 1) val = 1;
                                            editAmountItem(item, val);
                                        }}
                                        aria-label={`Cantidad de ${item.title}`}
                                    />

                                    <button
                                        className="quantity-btn"
                                        onClick={() => editAmountItem(item, item.amount + 1)}
                                        aria-label={`Aumentar cantidad de ${item.title}`}
                                    >
                                        <FaPlus />
                                    </button>
                                </div>

                                <div className="checkout__item-actions">
                                    <button
                                        className="checkout__remove-button"
                                        onClick={() => removeFromCart(item.id)}
                                        aria-label="Eliminar"
                                    >
                                        <FaTrash />
                                    </button>
                                </div>
                            </div>
                        ))}
                    </div>
                </div>

                <div className="checkout__summary-wrapper">
                    <button
                        className="checkout__back-link"
                        onClick={() => navigate('/home')}
                    >
                        ← Seguir comprando
                    </button>

                    <div className="checkout__summary">
                        <span className="checkout__total-label">Total:</span>
                        <span className="checkout__total-value">${total}</span>
                        <button className="checkout__confirm-button" onClick={handleConfirm}>
                            Confirmar pago
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default CheckoutPage;

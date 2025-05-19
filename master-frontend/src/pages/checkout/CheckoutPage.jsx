import React from 'react';
import {useNavigate} from 'react-router-dom';
import useCart from '../../hooks/useCart';
import './CheckoutPage.scss';

const CheckoutPage = () => {
    const {cart, clearCart,removeFromCart } = useCart();
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

            <div className="checkout__content">
                {/* 🧾 Columna izquierda: encabezados + lista */}
                <div className="checkout__left">
                    <div className="checkout__header">
                        <span className="checkout__header-product">Producto</span>
                        <span className="checkout__header-price">Precio</span>
                        <span className="checkout__header-remove"></span> {/* espacio para icono basura */}
                    </div>
                    <div className="checkout__list">
                        {cart.map((item) => (
                            <div key={item.id} className="checkout__item">
                                <div className="checkout__item-product">
                                    <img
                                        src={item.cover}
                                        alt={item.title}
                                        className="checkout__item-image"
                                    />
                                    <span className="checkout__item-title">{item.title}</span>
                                </div>

                                <div className="checkout__item-price-wrapper">
                                    <span className="checkout__item-price">${item.price.toFixed(2)}</span>
                                </div>

                                <button
                                    className="checkout__remove-button"
                                    onClick={() => removeFromCart(item.id)}
                                    aria-label="Eliminar"
                                >
                                    🗑️
                                </button>
                            </div>
                        ))}
                    </div>
                </div>

                {/* 💳 Columna derecha: resumen y botones */}
                <div className="checkout__summary">
                    <span className="checkout__total-label">Total:</span>
                    <span className="checkout__total-value">${total}</span>
                    <button className="checkout__confirm-button" onClick={handleConfirm}>
                        Confirmar pago
                    </button>
                    <button className="checkout__continue-button" onClick={() => navigate('/home')}>
                        Seguir comprando
                    </button>
                </div>
            </div>
        </div>
    );

};

export default CheckoutPage;

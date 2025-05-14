import { useNavigate } from "react-router-dom";
import "./CheckoutPage.scss";
import Cart from "../../components/Cart/Cart";
import { useCartContext } from "../../components/CartContext";

const CheckoutPage = () => {
  const { cart, clearCart } = useCartContext();
  const navigate = useNavigate();

  const total = cart
    .reduce((sum, item) => sum + item.price * item.amount, 0)
    .toFixed(2);

  const handleConfirm = () => {
    alert("Pedido realizado con éxito");
    clearCart();
    navigate("/home");
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
        <Cart />
        <div className="checkout__content__footer">
          <span className="checkout__total">Total: ${total}</span>
          <button className="checkout__confirm-button" onClick={handleConfirm}>
            Confirmar pago
          </button>
        </div>
      </div>
    </div>
  );
};

export default CheckoutPage;

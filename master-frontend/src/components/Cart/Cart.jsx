import CartItem from "../CartItem/CartItem";
import "./Cart.scss";
import { useCartContext } from "../CartContext";

const Cart = () => {
  const { cart, removeFromCart, editAmountItem } = useCartContext();

  const changeItemAmount = (item, amount) => {
    editAmountItem(item, amount);
  };

  return (
    <div className="cart">
      <h2 className="cart__title">Tus productos</h2>
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
                onEdit={(amount) => changeItemAmount(item, amount)}
              />
            ))}
          </ul>
        </>
      )}
    </div>
  );
};

export default Cart;

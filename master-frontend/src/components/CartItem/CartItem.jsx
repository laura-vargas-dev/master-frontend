import PropTypes from "prop-types";
import "./CartItem.scss";

const CartItem = ({ item, onRemove, onEdit }) => {
  const countItems = Array.from(Array(9).keys());
  return (
    <li className="cart-item">
      <img
        src={item.cover}
        alt={item.title}
        className="cart-item__image"
        onError={({ currentTarget }) => {
          currentTarget.onerror = null;
          currentTarget.src = "/covers/cover-general-book.png";
        }}
      />
      <div className="cart-item__info">
        <h4 className="cart-item__title">{item.title}</h4>
        <p className="cart-item__author">{item.author}</p>
      </div>
      <div className="cart-item__amount">
        ${item.price.toFixed(2)}
        <select
          className="cart-item__amount--select"
          onChange={(event) => onEdit(event.target.value)}
          value={item.amount}
        >
          {countItems.map((item) => (
            <option value={item.amount} key={"a" + item}>
              {item + 1}
            </option>
          ))}
        </select>
        ${item.amount * item.price.toFixed(2)}
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
};
CartItem.propTypes = {
  item: PropTypes.shape({
    cover: PropTypes.string.isRequired,
    title: PropTypes.string.isRequired,
    author: PropTypes.string.isRequired,
    amount: PropTypes.string.isRequired,
    price: PropTypes.number.isRequired,
  }).isRequired,
  onRemove: PropTypes.func.isRequired,
};
export default CartItem;

import { CinemaRouter } from "./router/BookRouter";
import { CartProvider } from "./components/CartContext";

export default function App() {
  return (
    <CartProvider>
      <CinemaRouter />
    </CartProvider>
  );
}

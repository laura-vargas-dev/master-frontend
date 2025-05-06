import { useState, useEffect } from 'react';

/**
 * Hook para gestionar el carrito de compras con persistencia en localStorage
 */
export default function useCart() {
  const [cart, setCart] = useState(() => {
    try {
      const saved = window.localStorage.getItem('cart');
      return saved ? JSON.parse(saved) : [];
    } catch {
      return [];
    }
  });

  useEffect(() => {
    try {
      window.localStorage.setItem('cart', JSON.stringify(cart));
    } catch (error) {
        console.error('Error al guardar en localStorage:', error);
    }
  }, [cart]);

  const addToCart = (book) => {
    setCart((prev) => [...prev, book]);
  };

  const removeFromCart = (id) => {
    setCart((prev) => prev.filter((item) => item.id !== id));
  };

  const clearCart = () => setCart([]);

  return { cart, addToCart, removeFromCart, clearCart };
}
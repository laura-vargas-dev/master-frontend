import React, {createContext, useContext} from 'react';
import useCart from '../hooks/useCart';

const CartContext = createContext();

export const CartProvider = ({children}) => {
    const cartData = useCart();
    console.log("CartProvider render, cart:", cartData.cart);


    return (
        <CartContext.Provider value={cartData}>
            {children}
        </CartContext.Provider>
    );
};

export const useCartContext = () => useContext(CartContext);

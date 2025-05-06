import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Landing from './pages/landing/Landing';
import Home from './pages/home/Home';
import NotFound from './pages/noFound/NotFound';
import BookPage from './pages/bookpage/BookPage';
import Header from './components/Header/Header';
import CheckoutPage from './pages/checkout/CheckoutPage';

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        {/* Landing page without Header */}
        <Route path="/" element={<Landing />} />

        {/* Main routes with Header */}
        <Route
          path="/home"
          element={
            <>
              <Header />
              <Home />
            </>
          }
        />
        <Route
          path="/book/:id"
          element={
            <>
              <Header />
              <BookPage />
            </>
          }
        />
        <Route
          path="/checkout"
          element={
            <>
              <Header />
               <CheckoutPage />
            </>
          }
        />

        {/* 404 Not Found */}
        <Route path="*" element={<NotFound />} />
      </Routes>
    </BrowserRouter>
  );
}

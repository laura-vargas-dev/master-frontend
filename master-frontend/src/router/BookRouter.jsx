import { BrowserRouter, Route, Routes } from "react-router-dom";
import Header from "../components/Header/Header";
import Home from "../pages/home/Home";
import BookPage from "../pages/bookPage/BookPage";
import CheckoutPage from "../pages/checkout/CheckoutPage";
import NotFound from "../pages/noFound/NotFound";
import Landing from "../pages/landing/Landing";

export const CinemaRouter = () => {
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
};

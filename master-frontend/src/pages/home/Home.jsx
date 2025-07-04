import React, { useState, useEffect } from "react";
import BookList from "../../components/BookList/BookList";
import { exampleData } from "../../utils/mockData";
import axios from "axios";
import "./Home.scss";
import SideBar from "../../components/Sidebar/Sidebar";

const Home = () => {
  const url =
    import.meta.env.VITE_API_URL_FILTERS || "http://localhost:8087/facets/books?";
  const [facets, setFacets] = useState({});
  const [filtered, setFiltered] = useState([]);
  const [selectedFacets, setSelectedFacets] = useState({});
  const [facetsUrl, setFacetsUrl] = useState(url);
  const [facetsQueryParams] = useState(new URLSearchParams());
  const [page, setPage] = useState(0);

  useEffect(() => {
    axios
      .get(`${facetsUrl}&page=${page}`)
      .then((response) => {
        setFacets(response.data.aggs);
        setFiltered((prevBooks) => [...prevBooks, ...response.data.books]);
      })
      .catch((error) => {
        console.error(
          "Error fetching the facets, using example data instead",
          error
        );
        setFacets(exampleData.aggs);
        setFiltered((prevBooks) => [...prevBooks, ...booksData]);
      });
  }, [facetsUrl, page]);

  const handleFacetChange = (facetKey, facetValue) => {
    setSelectedFacets((prevState) => {
      const newState = { ...prevState };

      if (newState[facetKey] && newState[facetKey].includes(facetValue)) {
        newState[facetKey] = newState[facetKey].filter(
          (value) => value !== facetValue
        );
      } else {
        newState[facetKey] = newState[facetKey]
          ? [...newState[facetKey], facetValue]
          : [facetValue];
      }
      return newState;
    });

    if (facetKey === "title" || facetKey === "author") {
      facetsQueryParams.set(facetKey, facetValue);
    } else {
      if (facetsQueryParams.has(facetKey)) {
        const selectedFacetValues = facetsQueryParams.get(facetKey).split(",");
        let newSelectedFacetValues = [];

        if (selectedFacetValues.includes(facetValue)) {
          if (selectedFacetValues.length === 1) {
            facetsQueryParams.delete(facetKey);
          } else {
            newSelectedFacetValues = selectedFacetValues.filter(
              (value) => value !== facetValue
            );
          }
        } else {
          selectedFacetValues.push(facetValue);
          newSelectedFacetValues = selectedFacetValues;
        }

        if (newSelectedFacetValues.length > 0) {
          facetsQueryParams.set(facetKey, newSelectedFacetValues.join(","));
        }
      } else {
        facetsQueryParams.set(facetKey, facetValue);
      }
    }

    setFacetsUrl(url + decodeURIComponent(facetsQueryParams.toString()));
    setPage(0);
    setFiltered([]);
  };

  const loadMore = () => {
    setPage((prevPage) => prevPage + 1);
  };

  return (
    <main className="home">
      <div className="FacetsApp">
        <SideBar
          facets={facets}
          onFacetChange={handleFacetChange}
          selectedFacets={selectedFacets}
        />

        <BookList books={filtered} loadMore={loadMore} />
      </div>
    </main>
  );
};

export default Home;

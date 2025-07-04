import React from "react";
import "./SideBar.scss";
import { translations } from "../../utils/Translations";

const SideBar = ({ facets, onFacetChange, selectedFacets }) => {
  return (
    <div className="sidebar">
      <h1>Filtros estáticos</h1>
      <div key={"name"} className="facet-category">
        <h3>Título (Completo)</h3>
        <div className="search-box">
          <input
            type="text"
            className="search-bar__input"
            placeholder="Buscar por título..."
            onInput={(e) => onFacetChange("title", e.target.value)}
          />
        </div>
      </div>
      <div key={"address"} className="facet-category">
        <h3>Autor (Parcial)</h3>
        <div className="search-box">
          <input
            type="text"
            className="search-bar__input"
            placeholder="Buscar por autor..."
            onInput={(e) => onFacetChange("author", e.target.value)}
          />
        </div>
      </div>

      {Object.keys(facets).length > 0 && <h1>Filtros dinámicos</h1>}

      {Object.keys(facets).map((facetKey) => (
        <div key={facetKey} className="facet-category">
          <h3>{translations.get(facetKey)}</h3>
          <div className="facet-options">
            {facets[facetKey].map(
              (facetValue) =>
                facetValue.count > 0 && (
                  <div
                    key={facetValue.key}
                    className={`facet-option ${
                      selectedFacets[facetKey] &&
                      selectedFacets[facetKey].includes(facetValue.key)
                        ? "selected"
                        : ""
                    }`}
                    onClick={() => onFacetChange(facetKey, facetValue.key)}
                  >
                    {translations.get(facetValue.key)
                      ? translations.get(facetValue.key)
                      : facetValue.key}{" "}
                    ({facetValue.count})
                  </div>
                )
            )}
          </div>
        </div>
      ))}
    </div>
  );
};

export default SideBar;

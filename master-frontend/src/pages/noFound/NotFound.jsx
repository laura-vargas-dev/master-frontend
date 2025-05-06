import React from 'react';
import  useNavigate  from '../../hooks/useRedirect';
import './NotFound.scss';


const NotFound = () => {
const navigate = useNavigate();

return (
  <div>
	<p>404 Página no encontrada</p>
	<button className="not-found__button" onClick={() => navigate('/home')}>
	  Ir a Inicio
	</button>
  </div>
);
};

export default NotFound;
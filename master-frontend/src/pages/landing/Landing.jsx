import React from 'react';
import  useRedirect  from '../../hooks/useRedirect'; // Custom hook to handle redirection
import './Landing.scss';

const Landing = () => {
useRedirect(5000, '/home');

return (
	<div>Bienvenido a BookStore Te redirigimos a la página principal en unos segundos...</div>
);   
};

export default Landing;

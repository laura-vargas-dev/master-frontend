import React from 'react';
import  useRedirect  from '../../hooks/useRedirect'; // Custom hook to handle redirection
import './Landing.scss';

const Landing = () => {
	useRedirect(5000, '/home');

	return (

			<div className='landing__contenedor'>
				<div className='landing__overlay'>
					<div className='landing__loader'>
						<div className='loader__spinner'></div>
						<p className='loader__text'>Cargando...</p>
					</div>
				</div>
			</div>
	);   
};

export default Landing;

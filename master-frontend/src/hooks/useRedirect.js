import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

/**

Redirige al usuario tras un tiempo de inactividad.

Cancela el timeout si detecta click o teclado.

@param {number} delay - Milisegundos a esperar (por defecto 5000)

@param {string} path - Ruta de redirección (por defecto '/home')
*/
export default function useRedirect(delay = 5000, path = '/home') {
const navigate = useNavigate();

useEffect(() => {
const timer = setTimeout(() => {
navigate(path);
}, delay);

const cancel = () => clearTimeout(timer);
window.addEventListener('click', cancel);
window.addEventListener('keydown', cancel);

return () => {
  clearTimeout(timer);
  window.removeEventListener('click', cancel);
  window.removeEventListener('keydown', cancel);
};

}, [delay, navigate, path]);
}
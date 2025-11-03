import { useState, useEffect } from 'react'
import './App.css'
import Registro from './Registro';

const API_URL = import.meta.env.VITE_API_URL;

function App() {
    const [message, setMessage] = useState('Carregando...');

    useEffect(() => {
        if (!API_URL) {
            setMessage('Erro: VITE_API_URL não está configurada.');
            return;
        }
        fetch(`${API_URL}/api/v1/test`)
            .then(res => res.json())
            .then(data => setMessage(data.message))
            .catch(() => setMessage('Erro ao conectar com o backend.'));
    }, []);

    return (
        <div className="App">
            <h1>Projeto Tendex</h1>
            <p>Mensagem do Backend: <strong>{message}</strong></p>

            <hr />

            {/* 2. ADICIONE SEU COMPONENTE AQUI */}
            <Registro />

        </div>
    )
}

export default App
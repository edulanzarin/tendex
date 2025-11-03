import { useState, useEffect } from 'react'
import './App.css'

// Esta linha mágica lê a variável que vamos configurar no Railway
const API_URL = import.meta.env.VITE_API_URL;

function App() {
    const [message, setMessage] = useState('Conectando ao backend...');

    useEffect(() => {
        // 1. Verifica se a variável de ambiente foi configurada
        if (!API_URL) {
            setMessage('Erro: A variável VITE_API_URL não está configurada no Railway.');
            return;
        }

        // 2. Tenta buscar a mensagem do backend
        fetch(`${API_URL}/api/v1/test`)
            .then(res => {
                // Se a resposta não for 'OK' (ex: erro 404, 500)
                if (!res.ok) {
                    throw new Error(`Erro no servidor: ${res.status}`);
                }
                return res.json(); // Converte a resposta para JSON
            })
            .then(data => {
                // Se deu tudo certo, mostra a mensagem
                setMessage(data.message);
            })
            .catch((error) => {
                // Se deu erro na conexão
                console.error('Erro ao buscar dados:', error);
                setMessage(`Falha ao conectar com o backend. Verifique o console (F12). URL: ${API_URL}`);
            });

    }, []); // O [] faz isso rodar apenas uma vez, quando o componente carrega

    return (
        <div className="App">
            <h1>Projeto Tendex</h1>
            <p>
                Mensagem do Backend: <strong>{message}</strong>
            </p>
        </div>
    )
}

export default App
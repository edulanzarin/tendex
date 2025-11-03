import React, { useState } from 'react';

const API_URL = import.meta.env.VITE_API_URL;

export default function Registro() {
    const [nome, setNome] = useState('');
    const [email, setEmail] = useState('');
    const [senha, setSenha] = useState('');
    const [mensagem, setMensagem] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault();
        setMensagem('Registrando...');

        const novoUsuario = { nome, email, senha };

        fetch(`${API_URL}/api/v1/usuarios/registrar`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(novoUsuario),
        })
            .then(res => {
                if (!res.ok) {
                    throw new Error('Erro ao registrar');
                }
                return res.json();
            })
            .then(data => {
                setMensagem(`Usuário ${data.nome} registrado com sucesso! (ID: ${data.id})`);
                setNome('');
                setEmail('');
                setSenha('');
            })
            .catch(err => {
                setMensagem(`Erro: ${err.message}. Verifique se o email já existe.`);
            });
    };

    return (
        <div style={{ border: '1px solid #555', padding: '20px', marginTop: '20px' }}>
            <h2>Formulário de Registro</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Nome: </label>
                    <input
                        type="text"
                        value={nome}
                        onChange={(e) => setNome(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label>Email: </label>
                    <input
                        type="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label>Senha: </label>
                    <input
                        type="password"
                        value={senha}
                        onChange={(e) => setSenha(e.target.value)}
                        required
                    />
                </div>
                <button type="submit">Registrar</button>
            </form>
            {mensagem && <p>{mensagem}</p>}
        </div>
    );
}
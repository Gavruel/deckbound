// DeckBound — Camada de comunicação com a API
// Todos os fetch() passam por aqui

const API_BASE = 'http://localhost:8080/api';

async function request(method, path, body = null) {
  const options = {
    method,
    headers: { 'Content-Type': 'application/json' },
  };
  if (body) options.body = JSON.stringify(body);

  const res = await fetch(`${API_BASE}${path}`, options);

  if (!res.ok) {
    const err = await res.json().catch(() => ({ erro: 'Erro desconhecido' }));
    throw new Error(err.erro || `Erro ${res.status}`);
  }

  if (res.status === 204) return null;
  return res.json();
}

// Players
export const api = {
  players: {
    listar: () => request('GET', '/players'),
    buscar: (id) => request('GET', `/players/${id}`),
    criar: (data) => request('POST', '/players', data),
    atualizar: (id, d) => request('PUT', `/players/${id}`, d),
    deletar: (id) => request('DELETE', `/players/${id}`),
    ranking: () => request('GET', '/players/ranking'),
  },

  matches: {
    listar: () => request('GET', '/matches'),
    buscar: (id) => request('GET', `/matches/${id}`),
    criar: (data) => request('POST', '/matches', data),
    deletar: (id) => request('DELETE', `/matches/${id}`),
  },

  commanders: {
    listar: () => request('GET', '/commanders'),
    buscar: (nome) => request('GET', `/commanders/search?name=${encodeURIComponent(nome)}`),
    salvar: (data) => request('POST', '/commanders', data),
  },

  comments: {
    listar: (matchId) => request('GET', `/matches/${matchId}/comments`),
    criar: (matchId, d) => request('POST', `/matches/${matchId}/comments`, d),
    deletar: (matchId, id) => request('DELETE', `/matches/${matchId}/comments/${id}`),
  },

  // Busca diretamente na API Scryfall (não passa pelo backend)
  scryfall: {
    buscar: async (nome) => {
      const res = await fetch(`https://api.scryfall.com/cards/named?fuzzy=${encodeURIComponent(nome)}`);
      if (!res.ok) throw new Error('Carta não encontrada no Scryfall');
      return res.json();
    },
    buscarLista: async (nome) => {
      const res = await fetch(`https://api.scryfall.com/cards/search?q=${encodeURIComponent(nome)}&unique=cards`);
      if (!res.ok) return { data: [] };
      return res.json();
    }
  }
};

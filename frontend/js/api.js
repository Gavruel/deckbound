const API_BASE = 'http://localhost:8080/api';

let PLAYGROUP_ID = '8bc192f9-b7db-4479-96a5-3711400b074a';

export function setPlaygroupId(id) {
  PLAYGROUP_ID = id;
}

async function request(method, path, body = null) {
  const options = {
    method,
    headers: { 'Content-Type': 'application/json' },
  };
  if (body) options.body = JSON.stringify(body);

  const res = await fetch(`${API_BASE}${path}`, options);

  if (!res.ok) {
    const err = await res.json().catch(() => ({ erro: 'unknown error' }));
    throw new Error(err.erro || `Erro ${res.status}`);
  }

  if (res.status === 204) return null;
  return res.json();
}

export const api = {
  players: {
    listar: () => request('GET', `/playgroups/${PLAYGROUP_ID}/players`),
    buscar: (id) => request('GET', `/playgroups/${PLAYGROUP_ID}/players/${id}`),
    criar: (data) => request('POST', `/playgroups/${PLAYGROUP_ID}/players`, data),
    atualizar: (id, d) => request('PUT', `/playgroups/${PLAYGROUP_ID}/players/${id}`, d),
    deletar: (id) => request('DELETE', `/playgroups/${PLAYGROUP_ID}/players/${id}`),
    ranking: () => request('GET', `/playgroups/${PLAYGROUP_ID}/players/ranking`),
  },

  matches: {
    listar: () => request('GET', `/playgroups/${PLAYGROUP_ID}/matches`),
    buscar: (id) => request('GET', `/playgroups/${PLAYGROUP_ID}/matches/${id}`),
    criar: (data) => request('POST', `/playgroups/${PLAYGROUP_ID}/matches`, data),
    deletar: (id) => request('DELETE', `/playgroups/${PLAYGROUP_ID}/matches/${id}`),
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

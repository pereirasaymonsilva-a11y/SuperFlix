const API_BASE = 'https://superflixapi.fit';

export const api = {
  // Buscar filmes
  getMovies: async (type = 'tmdb', genre = null, page = 1) => {
    let url = `${API_BASE}/lista?category=filme&type=${type}&format=json`;
    if (genre) url += `&genero=${genre}`;
    try {
      const response = await fetch(url);
      const data = await response.json();
      return { success: true, data };
    } catch (error) {
      return { success: false, error: error.message };
    }
  },

  // Buscar séries
  getSeries: async (type = 'tmdb', genre = null) => {
    let url = `${API_BASE}/lista?category=serie&type=${type}&format=json`;
    if (genre) url += `&genero=${genre}`;
    try {
      const response = await fetch(url);
      const data = await response.json();
      return { success: true, data };
    } catch (error) {
      return { success: false, error: error.message };
    }
  },

  // Buscar animes
  getAnimes: async (type = 'tmdb', genre = null) => {
    let url = `${API_BASE}/lista?category=animes&type=${type}&format=json`;
    if (genre) url += `&genero=${genre}`;
    try {
      const response = await fetch(url);
      const data = await response.json();
      return { success: true, data };
    } catch (error) {
      return { success: false, error: error.message };
    }
  },

  // Pesquisa geral
  search: async (query) => {
    const url = `${API_BASE}/lista?category=pesquisa&q=${encodeURIComponent(query)}&format=json`;
    try {
      const response = await fetch(url);
      const data = await response.json();
      return { success: true, data };
    } catch (error) {
      return { success: false, error: error.message };
    }
  },

  // Obter gêneros
  getGenres: async (category) => {
    const url = `${API_BASE}/lista?category=${category}&type=generos&format=json`;
    try {
      const response = await fetch(url);
      const data = await response.json();
      return { success: true, data };
    } catch (error) {
      return { success: false, error: error.message };
    }
  },

  // URL do player
  getPlayerUrl: (type, id, season = null, episode = null) => {
    if (type === 'movie') {
      return `${API_BASE}/filme/${id}#noEpList&color:ff0000&transparent`;
    } else {
      return `${API_BASE}/serie/${id}/${season}/${episode}#noEpList&color:ff0000&transparent`;
    }
  }
};
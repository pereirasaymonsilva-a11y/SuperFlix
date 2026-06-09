import React, { useState, useEffect } from 'react';
import {
  View,
  Text,
  Image,
  ScrollView,
  TouchableOpacity,
  StyleSheet,
  Modal,
  FlatList,
} from 'react-native';
import { api } from '../api/superflix';

export default function DetailScreen({ route, navigation }) {
  const { item, type } = route.params;
  const [showSeasonModal, setShowSeasonModal] = useState(false);
  const [selectedSeason, setSelectedSeason] = useState(1);
  const [selectedEpisode, setSelectedEpisode] = useState(1);
  const [seasons, setSeasons] = useState([]);

  useEffect(() => {
    if (type !== 'movies' && item.seasons) {
      const seasonsArray = Array.from({ length: item.seasons || 1 }, (_, i) => ({
        number: i + 1,
        episodes: item.episodes_per_season || 12,
      }));
      setSeasons(seasonsArray);
    }
  }, []);

  const handlePlay = () => {
    if (type === 'movies') {
      navigation.navigate('Player', {
        type: 'movie',
        id: item.id,
      });
    } else {
      navigation.navigate('Player', {
        type: 'serie',
        id: item.id,
        season: selectedSeason,
        episode: selectedEpisode,
      });
    }
  };

  return (
    <ScrollView style={styles.container}>
      <Image source={{ uri: item.poster || item.image }} style={styles.poster} />
      
      <View style={styles.infoContainer}>
        <Text style={styles.title}>{item.title || item.name}</Text>
        {item.year && <Text style={styles.year}>{item.year}</Text>}
        <Text style={styles.overview}>{item.overview || item.description}</Text>

        {type !== 'movies' && (
          <View style={styles.episodeSelector}>
            <TouchableOpacity
              style={styles.selectorButton}
              onPress={() => setShowSeasonModal(true)}
            >
              <Text style={styles.selectorButtonText}>
                Temporada {selectedSeason} - Episódio {selectedEpisode}
              </Text>
            </TouchableOpacity>
          </View>
        )}

        <TouchableOpacity style={styles.playButton} onPress={handlePlay}>
          <Text style={styles.playButtonText}>▶ ASSISTIR AGORA</Text>
        </TouchableOpacity>
      </View>

      <Modal visible={showSeasonModal} animationType="slide" transparent>
        <View style={styles.modalContainer}>
          <View style={styles.modalContent}>
            <Text style={styles.modalTitle}>Selecionar Episódio</Text>
            
            <Text style={styles.modalLabel}>Temporada:</Text>
            <FlatList
              horizontal
              data={seasons}
              keyExtractor={(item) => item.number.toString()}
              renderItem={({ item: season }) => (
                <TouchableOpacity
                  style={[
                    styles.seasonButton,
                    selectedSeason === season.number && styles.selectedButton,
                  ]}
                  onPress={() => setSelectedSeason(season.number)}
                >
                  <Text style={styles.seasonButtonText}>T{season.number}</Text>
                </TouchableOpacity>
              )}
              style={styles.seasonList}
            />

            <Text style={styles.modalLabel}>Episódio:</Text>
            <FlatList
              horizontal
              data={Array.from(
                { length: seasons[selectedSeason - 1]?.episodes || 12 },
                (_, i) => i + 1
              )}
              keyExtractor={(item) => item.toString()}
              renderItem={({ item: episode }) => (
                <TouchableOpacity
                  style={[
                    styles.episodeButton,
                    selectedEpisode === episode && styles.selectedButton,
                  ]}
                  onPress={() => setSelectedEpisode(episode)}
                >
                  <Text style={styles.episodeButtonText}>E{episode}</Text>
                </TouchableOpacity>
              )}
              style={styles.episodeList}
            />

            <TouchableOpacity
              style={styles.closeButton}
              onPress={() => setShowSeasonModal(false)}
            >
              <Text style={styles.closeButtonText}>Fechar</Text>
            </TouchableOpacity>
          </View>
        </View>
      </Modal>
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#000',
  },
  poster: {
    width: '100%',
    height: 500,
    resizeMode: 'cover',
  },
  infoContainer: {
    padding: 20,
  },
  title: {
    color: '#fff',
    fontSize: 28,
    fontWeight: 'bold',
  },
  year: {
    color: '#888',
    fontSize: 16,
    marginTop: 5,
  },
  overview: {
    color: '#ccc',
    fontSize: 14,
    marginTop: 15,
    lineHeight: 20,
  },
  episodeSelector: {
    marginTop: 20,
  },
  selectorButton: {
    backgroundColor: '#1a1a1a',
    padding: 15,
    borderRadius: 8,
    alignItems: 'center',
  },
  selectorButtonText: {
    color: '#fff',
    fontSize: 16,
  },
  playButton: {
    backgroundColor: '#ff0000',
    padding: 15,
    borderRadius: 8,
    alignItems: 'center',
    marginTop: 20,
  },
  playButtonText: {
    color: '#fff',
    fontSize: 18,
    fontWeight: 'bold',
  },
  modalContainer: {
    flex: 1,
    justifyContent: 'center',
    backgroundColor: 'rgba(0,0,0,0.9)',
  },
  modalContent: {
    backgroundColor: '#1a1a1a',
    margin: 20,
    borderRadius: 10,
    padding: 20,
    maxHeight: '80%',
  },
  modalTitle: {
    color: '#fff',
    fontSize: 20,
    fontWeight: 'bold',
    marginBottom: 15,
    textAlign: 'center',
  },
  modalLabel: {
    color: '#fff',
    fontSize: 16,
    marginTop: 10,
    marginBottom: 10,
  },
  seasonList: {
    maxHeight: 60,
  },
  seasonButton: {
    backgroundColor: '#333',
    padding: 10,
    marginRight: 10,
    borderRadius: 5,
  },
  episodeList: {
    maxHeight: 60,
  },
  episodeButton: {
    backgroundColor: '#333',
    padding: 10,
    marginRight: 10,
    borderRadius: 5,
  },
  selectedButton: {
    backgroundColor: '#ff0000',
  },
  seasonButtonText: {
    color: '#fff',
  },
  episodeButtonText: {
    color: '#fff',
  },
  closeButton: {
    backgroundColor: '#333',
    padding: 12,
    borderRadius: 8,
    alignItems: 'center',
    marginTop: 20,
  },
  closeButtonText: {
    color: '#fff',
    fontSize: 16,
  },
});
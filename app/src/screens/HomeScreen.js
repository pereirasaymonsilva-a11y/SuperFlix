import React, { useState, useEffect } from 'react';
import {
  View,
  FlatList,
  Text,
  StyleSheet,
  TouchableOpacity,
  RefreshControl,
} from 'react-native';
import { api } from '../api/superflix';
import MovieCard from '../components/MovieCard';
import LoadingSpinner from '../components/LoadingSpinner';

export default function HomeScreen({ navigation }) {
  const [movies, setMovies] = useState([]);
  const [series, setSeries] = useState([]);
  const [animes, setAnimes] = useState([]);
  const [loading, setLoading] = useState(true);
  const [refreshing, setRefreshing] = useState(false);
  const [activeTab, setActiveTab] = useState('movies');

  useEffect(() => {
    loadData();
  }, []);

  const loadData = async () => {
    const [moviesRes, seriesRes, animesRes] = await Promise.all([
      api.getMovies(),
      api.getSeries(),
      api.getAnimes(),
    ]);

    if (moviesRes.success) setMovies(moviesRes.data);
    if (seriesRes.success) setSeries(seriesRes.data);
    if (animesRes.success) setAnimes(animesRes.data);
    
    setLoading(false);
    setRefreshing(false);
  };

  const onRefresh = () => {
    setRefreshing(true);
    loadData();
  };

  const getCurrentData = () => {
    switch (activeTab) {
      case 'movies':
        return movies;
      case 'series':
        return series;
      case 'animes':
        return animes;
      default:
        return movies;
    }
  };

  const renderItem = ({ item }) => (
    <MovieCard
      movie={item}
      onPress={() => navigation.navigate('Detail', { item, type: activeTab })}
    />
  );

  if (loading) return <LoadingSpinner />;

  return (
    <View style={styles.container}>
      <View style={styles.tabs}>
        <TouchableOpacity
          style={[styles.tab, activeTab === 'movies' && styles.activeTab]}
          onPress={() => setActiveTab('movies')}
        >
          <Text style={[styles.tabText, activeTab === 'movies' && styles.activeTabText]}>
            Filmes
          </Text>
        </TouchableOpacity>
        <TouchableOpacity
          style={[styles.tab, activeTab === 'series' && styles.activeTab]}
          onPress={() => setActiveTab('series')}
        >
          <Text style={[styles.tabText, activeTab === 'series' && styles.activeTabText]}>
            Séries
          </Text>
        </TouchableOpacity>
        <TouchableOpacity
          style={[styles.tab, activeTab === 'animes' && styles.activeTab]}
          onPress={() => setActiveTab('animes')}
        >
          <Text style={[styles.tabText, activeTab === 'animes' && styles.activeTabText]}>
            Animes
          </Text>
        </TouchableOpacity>
      </View>

      <FlatList
        data={getCurrentData()}
        renderItem={renderItem}
        keyExtractor={(item, index) => `${activeTab}-${index}`}
        numColumns={2}
        showsVerticalScrollIndicator={false}
        refreshControl={
          <RefreshControl refreshing={refreshing} onRefresh={onRefresh} tintColor="#ff0000" />
        }
        contentContainerStyle={styles.list}
      />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#000',
  },
  tabs: {
    flexDirection: 'row',
    backgroundColor: '#1a1a1a',
    paddingVertical: 12,
  },
  tab: {
    flex: 1,
    alignItems: 'center',
    paddingVertical: 8,
  },
  activeTab: {
    borderBottomWidth: 2,
    borderBottomColor: '#ff0000',
  },
  tabText: {
    color: '#888',
    fontSize: 16,
    fontWeight: 'bold',
  },
  activeTabText: {
    color: '#ff0000',
  },
  list: {
    padding: 8,
  },
});
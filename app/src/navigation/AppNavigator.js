import React from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import HomeScreen from '../screens/HomeScreen';
import DetailScreen from '../screens/DetailScreen';
import PlayerScreen from '../screens/PlayerScreen';
import SearchScreen from '../screens/SearchScreen';

const Stack = createNativeStackNavigator();
const Tab = createBottomTabNavigator();

function HomeStack() {
  return (
    <Stack.Navigator
      screenOptions={{
        headerStyle: { backgroundColor: '#1a1a1a' },
        headerTintColor: '#fff',
        headerTitleStyle: { fontWeight: 'bold' },
      }}
    >
      <Stack.Screen name="HomeMain" component={HomeScreen} options={{ title: 'SuperFlix' }} />
      <Stack.Screen name="Detail" component={DetailScreen} options={{ title: 'Detalhes' }} />
      <Stack.Screen name="Player" component={PlayerScreen} options={{ title: 'Player', headerShown: false }} />
    </Stack.Navigator>
  );
}

function SearchStack() {
  return (
    <Stack.Navigator
      screenOptions={{
        headerStyle: { backgroundColor: '#1a1a1a' },
        headerTintColor: '#fff',
      }}
    >
      <Stack.Screen name="SearchMain" component={SearchScreen} options={{ title: 'Buscar' }} />
      <Stack.Screen name="Detail" component={DetailScreen} options={{ title: 'Detalhes' }} />
      <Stack.Screen name="Player" component={PlayerScreen} options={{ title: 'Player', headerShown: false }} />
    </Stack.Navigator>
  );
}

export default function AppNavigator() {
  return (
    <NavigationContainer>
      <Tab.Navigator
        screenOptions={{
          tabBarStyle: { backgroundColor: '#1a1a1a', borderTopColor: '#333' },
          tabBarActiveTintColor: '#ff0000',
          tabBarInactiveTintColor: '#888',
          headerShown: false,
        }}
      >
        <Tab.Screen name="Home" component={HomeStack} options={{ title: 'Início' }} />
        <Tab.Screen name="Search" component={SearchStack} options={{ title: 'Buscar' }} />
      </Tab.Navigator>
    </NavigationContainer>
  );
}
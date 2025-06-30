import { defineStore } from 'pinia';
import { reactive } from 'vue';
import { Client } from '@stomp/stompjs';
import type { ITourDTD } from './ITourDTD';
import { useInfo } from '@/composables/useInfo';
import type { IFrontendNachrichtEvent } from '@/services/IFrontendNachrichtEvent';

export const useTourenStore = defineStore('tourenstore', () => {
  const tourdata = reactive<{ ok: boolean; tourliste: ITourDTD[] }>({
    ok: false,
    tourliste: []
  });

  const { setzeInfo } = useInfo();
  let stompClient: Client | null = null;

  const updateTourListe = async () => {
    try {
      const response = await fetch('/api/tour');
      if (!response.ok) {
        throw new Error(response.statusText);
      }
      const data = await response.json();
      tourdata.tourliste = data;
      tourdata.ok = true;
      startTourLiveUpdate();
    } catch (error: any) {
      tourdata.tourliste = [];
      tourdata.ok = false;
      setzeInfo(`Fehler beim Abrufen der Tourdaten: ${error.message}`);
    }
  };

  const startTourLiveUpdate = () => {
    if (stompClient) {
      console.log('STOMP client already connected.');
      return;
    }

    stompClient = new Client({
      brokerURL: 'ws://localhost:8080/stompbroker/websocket',
      connectHeaders: {
        login: 'user',
        passcode: 'password'
      },
      debug: (str) => {
        console.log(str);
      },
      onConnect: () => {
        console.log('Connected to STOMP broker');
        stompClient!.subscribe('/topic/tour', message => {
          const event: IFrontendNachrichtEvent = JSON.parse(message.body);
          console.log(`Received event: ${JSON.stringify(event)}`);
          if (event.eventType === 'TOUR') {
            updateTourListe();
          }
        });
      },
      onStompError: frame => {
        console.error(`Broker reported error: ${frame.headers['message']}`);
        console.error(`Additional details: ${frame.body}`);
        setzeInfo(`Fehler beim STOMP-Update: ${frame.headers['message']}`);
      },
      onWebSocketError: event => {
        console.error('WebSocket error', event);
        setzeInfo('WebSocket-Fehler: Verbindung konnte nicht hergestellt werden.');
      }
    });

    stompClient.activate();
  };

  return {
    tourdata,
    updateTourListe
  };
});

export interface IFrontendNachrichtEvent {
    eventType: 'TOUR';
    id: number;
    operationType: 'CREATE' | 'UPDATE' | 'DELETE';
  }
  
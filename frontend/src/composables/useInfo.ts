import { ref, readonly } from 'vue';

const info = ref<string>('');

export function useInfo() {
  const setzeInfo = (msg: string) => {
    info.value = msg;
  };

  const loescheInfo = () => {
    info.value = '';
  };

  return {
    info: readonly(info),
    setzeInfo,
    loescheInfo,
  };
}

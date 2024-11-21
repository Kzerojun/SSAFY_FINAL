// src/plugins/piniaPersistPlugin.js
export function piniaPersistPlugin({ store }) {
  const storageKey = `pinia-${store.$id}`; // 각 스토어에 고유 키 생성

  // 1. localStorage에 저장된 상태를 스토어에 복원
  const storedState = localStorage.getItem(storageKey);
  if (storedState) {
    store.$patch(JSON.parse(storedState));
  }

  // 2. 스토어의 상태가 변경될 때 localStorage에 저장
  store.$subscribe((_mutation, state) => {
    localStorage.setItem(storageKey, JSON.stringify(state));
  });
}

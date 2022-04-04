#include <stdio.h>
#include <pthread.h>

int shared = 0;
pthread_mutex_t tMutex;

void *add(void *unused) {
  
  for(int i=0; i < 1000000; i++) { 
    pthread_mutex_lock(&tMutex);
    shared++;
    pthread_mutex_unlock(&tMutex);
  }
  return NULL;
}

int main() {
  pthread_t t;
  pthread_mutex_init(&tMutex, NULL);

  // Child thread starts running add
  pthread_create(&t, NULL, add, NULL);
  // Main thread starts running add
  
  add(NULL);
  // Wait until child thread t terminates
  pthread_join(t, NULL);
  printf("shared=%d\n", shared);
  return 0;
}

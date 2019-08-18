
#include "qsorts.h"
#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/times.h>
#include <sys/time.h>
#include <sys/resource.h>

#define N 16000000

int A[N];

void main () {
    srand(time(NULL));
    int i;
    int pos;
    int tmp;
    struct rusage start;
    struct rusage end;

    A[i] = 0;
    for (i = 1; i < N; i++) {
        A[i] = i / 100;
        pos = (((rand() % 0x7fff) << 16) | (rand() % 0xffff)) % (i+1);
        tmp = A[i];
        A[i] = A[pos];
        A[pos] = tmp;
    }

    // For Part 2:
    getrusage(RUSAGE_SELF, &start);

    // qsort295_1(A, N);
     qsort295_2(A, N);
    getrusage(RUSAGE_SELF, &end);
    printf("It took %ld microseconds to initialize the array.\n", end.ru_utime.tv_usec - start.ru_utime.tv_usec);


 // For Part 2:
    getrusage(RUSAGE_SELF, &start);

    // qsort295_1(A, N);
     qsort295_2(A, N);
    getrusage(RUSAGE_SELF, &end);
    printf("It took %ld microseconds to initialize the array.\n", end.ru_utime.tv_usec - start.ru_utime.tv_usec);
}


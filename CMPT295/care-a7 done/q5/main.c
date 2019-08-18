
#include "LL.h"
#include <stdio.h>
#include <stdlib.h>



#define N 250



#define NTESTS (2*N)
int A[N];
int P[NTESTS];
int Q[NTESTS];
int cycles[NTESTS];
long total;
int start_time = 150;
int end_time = 125;

void main () {
    srand(time(NULL));
    int i;
    List *L = newLL();
    int tmp, pos;


    // Build array
    for (i = 0; i < N; i++) {
        A[i] = 2*i;
        pos = (((rand() % 0x7fff) << 16) | (rand() % 0xffff)) % (i+1);
        tmp = A[i];
        A[i] = A[pos];
        A[pos] = tmp;
    }
    // Build LL
    for (i = 0; i < N; i++) {
        appendLL(L, A[i]);
    }


    // Time the code
    for (i = 0; i < NTESTS; i++) {
        asm volatile (
            "cpuid\n\t"
            "rdtscp\n\t"
            "movl %%eax, %0\n\t"
            : "=r" (start_time)
            : 
            : "rax", "rbx", "rcx", "rdx"
            );

        //P[i] = lsearch(A, N, i);     // choose array
        Q[i] = LLsearch(L, i);       // choose LL

        asm volatile (
            "cpuid\n\t"
            "rdtscp\n\t"
            "movl %%eax, %0\n\t"
            : "=r" (end_time)
            : 
            : "rax", "rbx", "rcx", "rdx"
            );

        cycles[i] = end_time - start_time;
        // if (cycles[i] >= 4000) i--;
    }


    // display results
    total = 0;
    for (i = 0; i < NTESTS; i++) {
        //printf("Sample %d completed in %d cycles.\n", i+1, cycles[i]);
        total += cycles[i];
    }
    printf("Average of %ld cycles.\n", total/NTESTS);


    // recycling is good for the environment
    freeLL(L);


    return;
}


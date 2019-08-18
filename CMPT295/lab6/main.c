
#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/times.h>
#include <sys/time.h>
#include <sys/resource.h>

int sum_plus(int *A, int n);

#define N 100
#define NTESTS 20

int A[N];
int P;
int Q;
int cycles[NTESTS];
long total;
int start_time = 150;
int end_time = 125;

void main () {
    srand(time(NULL));
    int i;
    struct rusage start;
    struct rusage end;

    // Init list
    Q = 0;
    for (i = 0; i < N; i++) {
        A[i] = rand() % 1024 - 512;
        if (A[i] > 0) Q += A[i];
    }


    // Run tests
    for (i = 0; i < NTESTS; i++) {
 //       getrusage(RUSAGE_SELF, &start);
      
    	asm volatile ( "cpuid\n\t" "rdtscp\n\t" "movl %%eax, %0\n\t" : "=r" (start_time) : : "rax", "rbx", "rcx", "rdx" );


        P = sum_plus(A, N);
        asm volatile ( "cpuid\n\t" "rdtscp\n\t" "movl %%eax, %0\n\t" : "=r" (end_time) : : "rax", "rbx", "rcx", "rdx" );

 //       getrusage(RUSAGE_SELF, &end);
        cycles[i] = end_time - start_time; //(end.ru_utime.tv_sec - start.ru_utime.tv_sec) * 10000000 + end.ru_utime.tv_usec - start.ru_utime.tv_usec;

        if (P != Q) {
            perror("Error:  sum mismatch"); return;
        }

        if(cycles[i] >=4000)
        	i--; 
    }


    // Display results
    total = 0;
    for (i = 0; i < NTESTS; i++) {
        printf("Sample %d completed in %d cycles.\n", i+1, cycles[i]);
        total += cycles[i];
    }
    printf("Average of %ld cycles.\n", total/NTESTS);

    return;
}


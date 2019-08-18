
#include <stdio.h>
#include <string.h>

void compute_ranks(float *F, int N, int *R, float *avg, float *passing_avg, int *num_passed);

#define N 30
#define NTESTS 20
#define NAMELENGTH 20

char buf[1024];
char names[N][NAMELENGTH];
float grades[N];
int ranks[N];
float avg;
float passing_avg;
int num_passed;
int cycles[NTESTS];
long total;
int start_time = 150;
int end_time = 125;
char *nul;

void main () {
    int i;

    // gather input
    for (i = 0; i < N; i++) {
        nul = fgets(buf, 1024, stdin);
        sscanf(buf, "%s %f", names[i], grades+i);
    }

    // timed test
    for (i = 0; i < NTESTS; i++) {
        asm volatile (
            "cpuid\n\t"
            "rdtscp\n\t"
            "movl %%eax, %0\n\t"
            : "=r" (start_time)
            : 
            : "rax", "rbx", "rcx", "rdx"
        );

        compute_ranks(grades, N, ranks, &avg, &passing_avg, &num_passed);

        asm volatile (
            "cpuid\n\t"
            "rdtscp\n\t"
            "movl %%eax, %0\n\t"
            : "=r" (end_time)
            : 
            : "rax", "rbx", "rcx", "rdx"
        );

        cycles[i] = end_time - start_time;
        if (cycles[i] >= 20000) { i--; continue; }
    }

    // display output
    printf("%15s  %5s  %4s\n", "Name", "Grade", "Rank");
    printf("%15s  %5s  %4s\n", "----------", "-----", "----");
    for (i = 0; i < N; i++) {
        printf("%15s  %2.2f  %3d\n", names[i], grades[i], ranks[i]);
    }
    putchar('\n');
    printf("Number who passed: %d/%d\n", num_passed, N);
    printf("  Passing average: %2.2f\n", passing_avg);
    printf("  Overall average: %2.2f\n", avg);
    putchar('\n');

    // display benchmark results
    total = 0;
    for (i = 0; i < NTESTS; i++) {
        printf("Sample %d completed in %d cycles.\n", i+1, cycles[i]);
        if (i >= NTESTS/2) {
            total += cycles[i];
        }
    }
    printf("Average of %ld cycles.\n", total/(NTESTS/2));

    return;
}


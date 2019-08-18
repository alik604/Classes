#include <string.h>
void compute_ranks(float *F, int N, int *R, float *avg, float *passing_avg, int *num_passed) {
    int i, j;

    *num_passed = 0;
    *avg = 0.0;
    *passing_avg = 0.0;
   

    int num_passed2 =0; // may impead preformance... IDK why 
    float avg2 =0; 
    float passing_avg2 =0; 
    
    float fmj =0; //majic bullet... 1 size fits all variable  
   
    // init ranks
    memset(R, 1, N*sizeof(*R));// used https://stackoverflow.com/a/9146410
    // for (i = 0; i < N; i++) {
    //     R[i] = 1;
    // }

    // compute ranks
    for (i = 0; i < N; i++) { // might be a bug here with array access 
        for (j = 0; j < N-4; j+=4) { // unrolling slows down 
            fmj = F[j];
            if (F[i] < fmj) {
                R[i] += 1;
            }
            if (F[i+1] < fmj) {
                R[i+1] += 1;
            }
            if (F[i+2] < fmj) {
                R[i+2] += 1;
            }
            if (F[i+3] < fmj) {
                R[i+3] += 1;
            }
            
        }

        fmj = F[i];
        avg2 += fmj;
        if (fmj >= 50.0) {
            passing_avg2 += fmj;
            num_passed2 += 1;
        }
    }

 // compute averages
    // for (i = 0; i < N-1; i+=2) { // works IFF n % 2 ==0, else its a bug 
    //     fmj = F[i];
    //     avg2 += fmj;
    //     if (fmj >= 50.0) {
    //         passing_avg2 += fmj;
    //         num_passed2 += 1;
    //     }
    //     fmj = F[i+1];
    //     avg2 += fmj;
    //     if (fmj >= 50.0) {
    //         passing_avg2 += fmj;
    //         num_passed2 += 1;
    //     }
       
    // }

    // check for div by 0
    if (N > 0) avg2 /= N;
    if (num_passed2) passing_avg2 /= num_passed2;


    *num_passed = num_passed2;
    *avg = avg2;
    *passing_avg = passing_avg2;
} // compute_ranks



//// khizr ali pardhan 301314376

int lsearch_2(int *A, int n, int target) {

    //.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.
    // Question 3(a):  Replace this code with the new algorithm 
    //.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.
    // int i;
    // for (i = 0; i < n; i++) {
    //     if (A[i] == target) {
    //         return i;
    //     }
    // }
    // return -1;

	if (n <=0)
		return -1;
	
	int tmp = A[n-1];
	A[n-1] = target;

	int i =0;
	while (A[i] != target){
		i++;
	}
	
	A[n-1]=tmp;

	if (i <n-1)
		return i;
	else if (A[n-1]== target)
		return n-1;
	else return -1; 

    //.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.
    
}  // lsearch_2



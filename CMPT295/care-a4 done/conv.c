
char conv(char *, char *, int);

char conv(char *x, char *h, int n) {
    char ret = 0;
    int i;
    for (i = 0; i < n; i++) {
        ret += x[i] * h[n-i-1];
    }
    return ret;
}

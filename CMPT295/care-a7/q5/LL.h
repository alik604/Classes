

typedef struct {
    int len;
    void *head;
    void *tail;
} List;


List *newLL(void);
void freeLL(List *);
void appendLL(List *L, int el);
void LLdump(List *L);


int LLsearch(List *L, int target);


int lsearch(int *A, int n, int target);


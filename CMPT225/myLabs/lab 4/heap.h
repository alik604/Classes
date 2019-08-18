using namespace std;

class Heap {

    public:
    Heap();
    ~Heap();
    bool empty() const {
        return hSize == 0;
    };
    void insert(int priority);
    int extractMin();
    private:
        class Pair {// not a real pair 
            public:
                int priority;
        };
    Pair * A;
    int hCapacity;
    int hSize;
    void trickleUp(int i);
    void trickleDown(int i);
    void heapify();
    void swap(int i, int j);
};

Heap::Heap() {
    hCapacity = 1000000000;
    A = new Pair[hCapacity];
    hSize = 0;
}

Heap::~Heap() {
    delete[] A;
}

void Heap::insert(int priority) {
    A[hSize].priority = priority;
    trickleUp(hSize);
    hSize++;
}

void Heap::trickleUp(int i) {
    int parent = (i - 1) / 2;
    while (i > 0 && A[parent].priority < A[i].priority) {
       
        swap(parent,i);

        trickleUp(parent);
    }
}

void Heap::swap(int i, int j) {
    Pair temp = A[i];
    A[i] = A[j];
    A[j] = temp;
}

int Heap::extractMin() {
    Pair temp = A[0];
    A[0] = A[--hSize];
    heapify();
    trickleDown(0);
    return temp.priority;
}

void Heap::trickleDown(int i) {
    int backup = i;
    int leftPos = 2 * i + 1;
    int rightPos = 2 * i + 2;

    if (leftPos < hSize && A[leftPos].priority < A[i].priority)//check if valid pos && lesser prior
        {backup = leftPos;}

    if (rightPos < hSize && A[backup].priority > A[rightPos].priority)
        {backup = rightPos;}

    if (backup != i) {
        swap(i, backup);
        trickleDown(backup);
    }
}

void Heap::heapify() {
    for (int i = (hSize / 2) - 1; i >= 0; i--) trickleDown(i);
}
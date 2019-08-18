using namespace std;

class Queue
{
    public:
        Queue();
        ~Queue();
        void enqueue( int item );
        bool Search(int item);
        bool empty();
    private:
        class node{
            public:
                node(int new_data, node * next_node ){
                    data = new_data;
                    next = next_node;
                }
            int data;
            node * next;
        };
            node * next;
            node * back;
            int size;
};

Queue::Queue(){  
    next = NULL;
    back = NULL; 
    size = 0;
}

Queue::~Queue(){ 
    while (next != NULL) {
        node *pr = next;
        pr = next->next;
        delete next;
        next = pr;
    }
    next = NULL;
    back = NULL;
    size = 0;
}
    
void Queue::enqueue( int x ){
    node * p = new node(x, NULL);
    if (next == NULL) {
        next = p;
        back = p;
    }
    else {
        back->next = p;
        back = p;
        back->next = NULL;
    }
    size++;
}

bool Queue::Search(int x){
    node * p = next;
    while(p != NULL){
        if(p -> data == x){
            return true;
        }
        p = p -> next;
    }
    return false;
}    
    
bool Queue::empty(){  
    return size == 0;
}
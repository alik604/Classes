

#ifndef CMPT225_STRINGLINKEDLIST_H
#define CMPT225_STRINGLINKEDLIST_H


#include <string>
#include "StringNode.h"

class StringLinkedList { // a linked list of strings
public:
    StringLinkedList(); // empty list constructor
    ~StringLinkedList(); // destructor
    bool empty() const; // is list empty?
    const std::string &front() const; // get front element
    void addFront(const std::string &e); // add to front of list
    void removeFront(); // remove front item list
    int getSize();

private:
    StringNode *head; // pointer to the head of list
};

#endif //CMPT225_STRINGLINKEDLIST_H

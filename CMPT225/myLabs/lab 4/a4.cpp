/**
 @author 301314376 kpardhan khizr pardhan
 @date aug 2nd 2018 
 @version 1.0 (final) 
 @return via print the max numb in L1 thats not in L2
*/

#include <iostream>
#include <fstream>
#include <string>
#include "queue.h"
#include "heap.h"

using namespace std;
//DONE! TODO there cant be a new line at end of .txt
int main(int argc, char *argv[]){

	string filename = argv[1];
	string line;
	ifstream myfile(filename);
	Heap L1; //myHeap
	Queue L2; //myQueue

	bool List2 = false; 
	int x ;

	cout << "301314376 kpardhan khizr pardhan" << endl;

try{
	if (myfile.is_open()) {
	    while (getline(myfile, line)) {
	        int x = std::stoi(line);
	        if (x == 0) { //elseif is ok, since all are mutully exclusive
	            List2 = true;
	        }else if (!List2) { 
	            L1.insert(x);
	        } else {
	            L2.enqueue(x);
	        }
	    }
	    myfile.close();
	} else {
	    cout << "Unable to open file";
	}

}catch (const std::invalid_argument){
	cout << "invalid output exp!"<<endl; 
	//my program work if you end with black line, or with a single letter :)
	//bonus mark plz?  
}

	while (!L1.empty()) {
	    x = L1.extractMin();
	    if (L2.empty()) {
	        cout << x << endl;
	        return 0; 
	        // best way to end program in c++?
	        //assuming this is equvilent to java's System.exit(0); 
	    }else if (!L2.Search(x)) {
	        cout << x << endl;
	        return 0;
	    }
	}
/**
while heap is not empty
	let x be max elem
	if queue is empty, print x
	otherwise, if queue does not contain x, print x

its not of the above cases.... print "NONE"  (in caps) 
*/
	cout << "NONE" << endl;
	return 0;
}
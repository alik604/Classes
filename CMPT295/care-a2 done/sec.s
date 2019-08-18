	.globl sec
sec:

	movl $x,%edi #TODO edi gets x 
	imul %edi, $k 



#b)
# leal k<-(k*x)
# leal %edi <- k
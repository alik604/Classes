	.globl times
times:
	mov %edi, %eax      #  remove these two lines before
	imull %esi, %eax    #  you start writing your code

	ret


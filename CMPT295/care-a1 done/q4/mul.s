	.globl times
times:
# khizr ali pardhan 301314376


 	## TODO change esp to rcx...need help

#edi = 6
#esi = 7
	mov $0, %eax    
	mov $0, %ecx		
loop:   cmpl %ecx ,%esi
	jle endloop
	addl %edi,%eax
	incl %ecx
	jmp loop


endloop:

	ret     # ret %eax 

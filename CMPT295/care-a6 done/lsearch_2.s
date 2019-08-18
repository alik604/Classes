#TODO 
# map 
#comments 
# var map:
	#    %eax - index, or, if not found, -1
	#    %rdi - int *A
	#    %esi - int n
	#    %edx - int target
	#    %ecx - incrementing variable (index)
	#    %r8d  - int *tmp

	.globl	lsearch_2
lsearch_2:

	testl	%esi, %esi # test if i is <= 0
	jle	NotFound
	movslq	%esi, %rax
	leaq	-4(%rdi,%rax,4), %rax   # rax/tmp = A[n-1]
	movl	(%rax), %r8d 			# A[n-1] = target
	movl	%edx, (%rax)			# tmp to m[rax] 
	cmpl	(%rdi), %edx			# if A[0] == target
	je	clearEcx 						# clear ecx and jmp to compare
									# i=0
	addq	$4, %rdi 				# load in next value of A
	xorl	%ecx, %ecx 				#clear ecx

loop:							# do 
	addq	$4, %rdi				# load in next value of A 
	addl	$1, %ecx  				# i += 1
	cmpl	-4(%rdi), %edx			# loop till target is found
	jne	loop					# while A[i] != target 

compare:
	movl	%r8d, (%rax)			# tmp to M[rax] 
	leal	-1(%rsi), %eax     		# eax is n-1
	cmpl	%ecx, %eax			# if (n-1 > i)
	jg	found  						# ret i
	cmpl	%edx, %r8d  		# if (A[n-1] != target)
	jne	NotFound					# return -1 
	rep ret						## return n-1
	
found:
	movl	%ecx, %eax
	ret 							## return i

NotFound:
	movl	$-1, %eax
	ret 							## return -1

clearEcx:
	xorl	%ecx, %ecx  			# i = 0
	jmp	compare    					
	
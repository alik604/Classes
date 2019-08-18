# khizr ali pardhan | 2/15/2019
	.globl conv_arr
conv_arr:

#OL for pram regs: rdi,rsi,rdx,rcx,r8,r9...stack 
# x* is rdi 
# n  is rsi 
# h* is rdx
# m  is rcx 
# ladj is r12
# radj is r13
# i    is r14
# max  is r15  

# for i from 0 to n+m-2 do
# 	ladj <- min(i+1, m)
# 	radj <- m - min(m+n-(i+1), m)
# 	result[i] <- conv(x + (i+1-ladj), h + radj, ladj-radj)

	push	%r15
	push	%r14
	push	%r13
	push	%r12

 	xor %r14, %r14 # r14 = 0 

	#movq %rsi, %r15
	#addq %rcx, %r15
	#subq $1, %r15 # 1???
	leaq -1(%rsi,%rcx), %r15 #ebx= m+n-1
	#^ this single line replaces the three above it 

loop:
	cmpq %r14, %r15
	je end 

## 1st call 
	push %rdi
	push %rsi
                  
	leaq 1(%r14),%rdi 			# rdi i+1 
	movq %rcx, %rsi             # rsi = m

	call min
	movq %rax, %r12             # r12 is ladj 
## 2nd call 

	pop %rsi
	push %rsi

	mov %r15,%rdi # why repeat a computation... //leaq -1(%rsi,%rcx), %r15
	subq %r14, %rdi    

	movq %rcx, %rsi 

	call min
	movq %rcx, %r13 
	subq %rax, %r13             # r13 is radj; radj= m-retValue 

#conv

	pop %rsi
	pop %rdi # restore original values (from prams)  

	# `call conv` messes with 5 regs 
	push %rdi 
	push %rsi
	push %rdx
	push %rcx
	push %r8

	addq %r14, %rdi
	addq $1, %rdi
	subq %r12, %rdi       # rdi = i+1 -ladj

	leaq (%rdx,%r13),%rsi #rsi = h + radj

	movq %r12, %rdx
	subq %r13, %rdx       #rdx = ladj-radj

	call conv  #messes with 5 regs 

	# `call conv` messes with 5 regs; restore regs 
	pop %r8  
	pop %rcx
	pop %rdx
	pop %rsi
	pop %rdi 
	
	movb %al, (%r8,%r14) #i + array = eax //the return value  

	inc %r14
	jmp loop # if disabled 0 in result[4]. many return values and sigfault 

end:
	
	pop	%r12
	pop	%r13
	pop	%r14
	pop	%r15	
	ret

#khizr ali pardhan- 301314376

#algorithum is called "binary search root finding algorithum"

#educational source: https://stackoverflow.com/questions/39779975/square-root-in-assembly-how-to-shift-and-change-bits
# research and code on the same day coding (as we were told to do so during lecture 1). 
	.globl sqrt
# register used: eax (instructor given),rcx (dec for loop),rdx (mask),r11 (for result^2) used
sqrt:
		movl $0, %eax # our unsigned 32bit quant ## rax???????
		mov $15, %rcx # decrement variable 
		mov $0x8000, %rdx# mask = 65536, or 2^16 

		jmp loop 

changeBitAndRestart: #changeBit,dec,shr util label
	xor %rdx,%rax  # change the kth bit back to 0	
	dec %rcx  #decrement variable
	shr $1, %rdx #1 step to the right 

	jmp loop #uneeded,but good practice 

loop:
	or %rdx,%rax #guaranteed 1 in kth position 
	movq %rax,%r11 #copy rax to r11 
	imulq %rax,%r11 # r11 is rax^2 

	cmpq %r11,%rdi #edi will contain arg x 
	jl changeBitAndRestart #"xor %rdx,%rax" will NOT work here :(
#dear TA, why is that? (stackoverflow says we can do a 'inc' after a 'cmp')

	dec %rcx # decrement variable
	shr $1, %rdx #1 step to the right 

	cmpq $0,%rcx # if rcx is >= 0, goto loop 
	jge loop 

	jmp endloop ##uneeded,but good practice 


endloop:  ##uneeded,but good practice 
  
	ret # we were given "movl $0, %eax",
#rather then "mov $0, %rax", so i didnt change it 

	.file	"main.c"
	.section	.text.unlikely,"ax",@progbits
.LCOLDB0:
	.text
.LHOTB0:
	.p2align 4,,15
	.globl	lsearch_1
	.type	lsearch_1, @function
lsearch_1:
.LFB39:
	.cfi_startproc
	testl	%esi, %esi
	movl	$-1, %eax
	jle	.L2
	cmpl	(%rdi), %edx
	je	.L6
	addq	$4, %rdi
	xorl	%eax, %eax
	jmp	.L3
	.p2align 4,,10
	.p2align 3
.L4:
	addq	$4, %rdi
	cmpl	%edx, -4(%rdi)
	je	.L2
.L3:
	addl	$1, %eax
	cmpl	%eax, %esi
	jne	.L4
	movl	$-1, %eax
	ret
.L6:
	xorl	%eax, %eax
.L2:
	rep ret
	.cfi_endproc
.LFE39:
	.size	lsearch_1, .-lsearch_1
	.section	.text.unlikely
.LCOLDE0:
	.text
.LHOTE0:
	.section	.rodata.str1.1,"aMS",@progbits,1
.LC1:
	.string	"Initializing array . . ."
.LC2:
	.string	"Array initialized. . ."
	.section	.rodata.str1.8,"aMS",@progbits,1
	.align 8
.LC3:
	.string	"Starting test of function %d . . .\n"
	.section	.rodata.str1.1
.LC4:
	.string	"Error: A[N] not consistent.\n"
	.section	.rodata.str1.8
	.align 8
.LC5:
	.string	"Error: A[N-1] not consistent.\n"
	.align 8
.LC6:
	.string	"It took %ld microseconds to run function %d.\n"
	.section	.rodata.str1.1
.LC7:
	.string	"Totalling work %d . . .\n"
	.section	.rodata.str1.8
	.align 8
.LC8:
	.string	"Error: Results of searches disagree.\n"
	.section	.rodata.str1.1
.LC9:
	.string	"Total misses = %ld, %ld\n"
	.section	.rodata.str1.8
	.align 8
.LC10:
	.string	"Total of positions searched = %ld, %ld\n"
	.section	.text.unlikely
.LCOLDB11:
	.section	.text.startup,"ax",@progbits
.LHOTB11:
	.p2align 4,,15
	.globl	main
	.type	main, @function
main:
.LFB38:
	.cfi_startproc
	pushq	%r15
	.cfi_def_cfa_offset 16
	.cfi_offset 15, -16
	pushq	%r14
	.cfi_def_cfa_offset 24
	.cfi_offset 14, -24
	movl	$.LC1, %edi
	pushq	%r13
	.cfi_def_cfa_offset 32
	.cfi_offset 13, -32
	pushq	%r12
	.cfi_def_cfa_offset 40
	.cfi_offset 12, -40
	movl	$A, %r13d
	pushq	%rbp
	.cfi_def_cfa_offset 48
	.cfi_offset 6, -48
	pushq	%rbx
	.cfi_def_cfa_offset 56
	.cfi_offset 3, -56
	movl	$A+4, %r12d
	movl	$1, %ebp
	movl	$1431655766, %ebx
	subq	$3576, %rsp
	.cfi_def_cfa_offset 3632
	movq	%fs:40, %rax
	movq	%rax, 3560(%rsp)
	xorl	%eax, %eax
	movq	$lsearch_1, 304(%rsp)
	movq	$lsearch_2, 312(%rsp)
	call	puts
	xorl	%edi, %edi
	xorl	%eax, %eax
	movl	$0, A(%rip)
	call	time
	movl	%eax, %edi
	call	srand
	.p2align 4,,10
	.p2align 3
.L10:
	movl	%ebp, %eax
	imull	%ebx
	movl	%ebp, %eax
	sarl	$31, %eax
	subl	%eax, %edx
	leal	(%rdx,%rdx,2), %eax
	xorl	%edx, %edx
	cmpl	%eax, %ebp
	leal	0(%rbp,%rbp,2), %eax
	sete	%dl
	addl	$1, %ebp
	addq	$4, %r12
	addl	%edx, %eax
	movl	%eax, -4(%r12)
	call	rand
	movl	%eax, %r14d
	call	rand
	movslq	%r14d, %rcx
	movl	%r14d, %edx
	imulq	$-2147418109, %rcx, %rcx
	sarl	$31, %edx
	shrq	$32, %rcx
	addl	%r14d, %ecx
	sarl	$14, %ecx
	subl	%edx, %ecx
	movl	%ecx, %edx
	sall	$15, %edx
	subl	%ecx, %edx
	movslq	%eax, %rcx
	imulq	$-2147450879, %rcx, %rcx
	subl	%edx, %r14d
	cltd
	sall	$16, %r14d
	shrq	$32, %rcx
	addl	%eax, %ecx
	sarl	$15, %ecx
	subl	%edx, %ecx
	movl	%ecx, %edx
	sall	$16, %edx
	subl	%ecx, %edx
	subl	%edx, %eax
	orl	%r14d, %eax
	cltd
	idivl	%ebp
	movl	-4(%r12), %eax
	movslq	%edx, %rdx
	movl	A(,%rdx,4), %ecx
	movl	%ecx, -4(%r12)
	cmpl	$5000000, %ebp
	movl	%eax, A(,%rdx,4)
	jne	.L10
	movl	$A+20000000, %eax
	movl	(%rax), %r12d
	movabsl	A+19999996, %eax
	movl	$.LC2, %edi
	leaq	352(%rsp), %r15
	movl	$lsearch_1, %ebp
	movl	%eax, (%rsp)
	movq	%r15, %rbx
	call	puts
	leaq	304(%rsp), %rax
	movl	$0, 4(%rsp)
	movq	%rax, 8(%rsp)
.L16:
	addl	$1, 4(%rsp)
	movl	$.LC3, %esi
	movl	$1, %edi
	movl	4(%rsp), %eax
	xorl	%r14d, %r14d
	movl	%eax, %edx
	xorl	%eax, %eax
	call	__printf_chk
	leaq	16(%rsp), %rsi
	xorl	%edi, %edi
	call	getrusage
	jmp	.L14
	.p2align 4,,10
	.p2align 3
.L11:
	movl	$A, %eax
	movl	(%rsp), %edi
	cmpl	19999996(%rax), %edi
	jne	.L28
	addq	$1, %r14
	cmpq	$400, %r14
	je	.L29
.L14:
	movl	%r14d, %edx
	movl	$5000000, %esi
	movl	$A, %edi
	call	*%rbp
	movl	%eax, (%rbx,%r14,4)
	cmpl	20000000(%r13), %r12d
	je	.L11
	movl	$.LC4, %edi
	call	perror
.L9:
	movq	3560(%rsp), %rax
	xorq	%fs:40, %rax
	jne	.L30
	addq	$3576, %rsp
	.cfi_remember_state
	.cfi_def_cfa_offset 56
	popq	%rbx
	.cfi_def_cfa_offset 48
	popq	%rbp
	.cfi_def_cfa_offset 40
	popq	%r12
	.cfi_def_cfa_offset 32
	popq	%r13
	.cfi_def_cfa_offset 24
	popq	%r14
	.cfi_def_cfa_offset 16
	popq	%r15
	.cfi_def_cfa_offset 8
	ret
.L28:
	.cfi_restore_state
	movl	$.LC5, %edi
	call	perror
	jmp	.L9
.L29:
	leaq	160(%rsp), %rsi
	xorl	%edi, %edi
	addq	$1600, %rbx
	call	getrusage
	movq	160(%rsp), %rdx
	subq	16(%rsp), %rdx
	xorl	%eax, %eax
	movl	4(%rsp), %r14d
	movl	$.LC6, %esi
	movl	$1, %edi
	imulq	$1000000, %rdx, %rdx
	movl	%r14d, %ecx
	subq	24(%rsp), %rdx
	addq	168(%rsp), %rdx
	call	__printf_chk
	addq	$8, 8(%rsp)
	cmpl	$2, %r14d
	jne	.L31
	movl	$1, %edi
	movl	$3, %edx
	movl	$.LC7, %esi
	xorl	%eax, %eax
	call	__printf_chk
	movq	$0, 320(%rsp)
	movq	$0, 328(%rsp)
	leaq	1952(%rsp), %r9
	movq	$0, 336(%rsp)
	movq	$0, 344(%rsp)
	leaq	352(%rsp), %rdi
	jmp	.L17
	.p2align 4,,10
	.p2align 3
.L18:
	addq	%rdx, (%rax)
.L19:
	addq	$8, %rcx
	addq	$1600, %rsi
	addq	$8, %rax
	cmpq	%rdi, %rcx
	jne	.L20
	movl	1600(%r15), %eax
	cmpl	%eax, (%r15)
	jne	.L32
	addq	$4, %r15
	cmpq	%r9, %r15
	je	.L22
.L17:
	leaq	336(%rsp), %rcx
	leaq	320(%rsp), %rax
	movq	%r15, %rsi
.L20:
	movslq	(%rsi), %rdx
	cmpl	$-1, %edx
	jne	.L18
	addq	$1, (%rcx)
	addq	$5000000, (%rax)
	jmp	.L19
.L32:
	movl	$.LC8, %edi
	call	perror
	jmp	.L9
.L22:
	movq	344(%rsp), %rcx
	movq	336(%rsp), %rdx
	movl	$.LC9, %esi
	movl	$1, %edi
	xorl	%eax, %eax
	call	__printf_chk
	movq	328(%rsp), %rcx
	movq	320(%rsp), %rdx
	movl	$.LC10, %esi
	movl	$1, %edi
	xorl	%eax, %eax
	call	__printf_chk
	jmp	.L9
.L31:
	movq	8(%rsp), %rax
	movq	(%rax), %rbp
	jmp	.L16
.L30:
	call	__stack_chk_fail
	.cfi_endproc
.LFE38:
	.size	main, .-main
	.section	.text.unlikely
.LCOLDE11:
	.section	.text.startup
.LHOTE11:
	.comm	A,20000000,32
	.ident	"GCC: (Ubuntu 5.4.0-6ubuntu1~16.04.11) 5.4.0 20160609"
	.section	.note.GNU-stack,"",@progbits

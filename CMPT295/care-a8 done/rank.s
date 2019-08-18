	.file	"rank.c"
	.section	.text.unlikely,"ax",@progbits
.LCOLDB2:
	.text
.LHOTB2:
	.p2align 4,,15
	.globl	compute_ranks
	.type	compute_ranks, @function
compute_ranks:
.LFB24:
	.cfi_startproc
	pxor	%xmm6, %xmm6
	pushq	%r14
	.cfi_def_cfa_offset 16
	.cfi_offset 14, -16
	pushq	%r13
	.cfi_def_cfa_offset 24
	.cfi_offset 13, -24
	pushq	%r12
	.cfi_def_cfa_offset 32
	.cfi_offset 12, -32
	movq	%rdx, %r10
	pushq	%rbp
	.cfi_def_cfa_offset 40
	.cfi_offset 6, -40
	pushq	%rbx
	.cfi_def_cfa_offset 48
	.cfi_offset 3, -48
	movslq	%esi, %rdx
	movl	%esi, %ebx
	salq	$2, %rdx
	movq	%rdi, %rbp
	subq	$16, %rsp
	.cfi_def_cfa_offset 64
	movl	$0, (%r9)
	movss	%xmm6, (%rcx)
	movss	%xmm6, (%r8)
	movl	$1, %esi
	movq	%r10, %rdi
	movss	%xmm6, 12(%rsp)
	movq	%rcx, %r12
	movq	%r8, %r13
	movq	%r9, %r14
	call	memset
	testl	%ebx, %ebx
	movss	12(%rsp), %xmm6
	jle	.L19
	leal	-5(%rbx), %esi
	movq	%rax, %rdx
	leal	-1(%rbx), %eax
	movaps	%xmm6, %xmm5
	movq	%rbp, %rcx
	shrl	$2, %esi
	leaq	4(%rbp,%rax,4), %rdi
	xorl	%r8d, %r8d
	addq	$1, %rsi
	salq	$4, %rsi
	addq	%rbp, %rsi
	.p2align 4,,10
	.p2align 3
.L3:
	cmpl	$4, %ebx
	movss	(%rcx), %xmm1
	jle	.L17
	movss	4(%rcx), %xmm4
	movq	%rbp, %rax
	movss	8(%rcx), %xmm3
	movss	12(%rcx), %xmm2
	.p2align 4,,10
	.p2align 3
.L12:
	movss	(%rax), %xmm0
	ucomiss	%xmm1, %xmm0
	jbe	.L4
	addl	$1, (%rdx)
.L4:
	ucomiss	%xmm4, %xmm0
	jbe	.L6
	addl	$1, 4(%rdx)
.L6:
	ucomiss	%xmm3, %xmm0
	jbe	.L8
	addl	$1, 8(%rdx)
.L8:
	ucomiss	%xmm2, %xmm0
	jbe	.L10
	addl	$1, 12(%rdx)
.L10:
	addq	$16, %rax
	cmpq	%rsi, %rax
	jne	.L12
.L17:
	ucomiss	.LC1(%rip), %xmm1
	addss	%xmm1, %xmm5
	jb	.L13
	addss	%xmm1, %xmm6
	addl	$1, %r8d
.L13:
	addq	$4, %rcx
	addq	$4, %rdx
	cmpq	%rdi, %rcx
	jne	.L3
	pxor	%xmm0, %xmm0
	testl	%r8d, %r8d
	cvtsi2ss	%ebx, %xmm0
	divss	%xmm0, %xmm5
	je	.L2
	pxor	%xmm0, %xmm0
	cvtsi2ss	%r8d, %xmm0
	divss	%xmm0, %xmm6
.L2:
	movl	%r8d, (%r14)
	movss	%xmm5, (%r12)
	movss	%xmm6, 0(%r13)
	addq	$16, %rsp
	.cfi_remember_state
	.cfi_def_cfa_offset 48
	popq	%rbx
	.cfi_def_cfa_offset 40
	popq	%rbp
	.cfi_def_cfa_offset 32
	popq	%r12
	.cfi_def_cfa_offset 24
	popq	%r13
	.cfi_def_cfa_offset 16
	popq	%r14
	.cfi_def_cfa_offset 8
	ret
.L19:
	.cfi_restore_state
	xorl	%r8d, %r8d
	movaps	%xmm6, %xmm5
	jmp	.L2
	.cfi_endproc
.LFE24:
	.size	compute_ranks, .-compute_ranks
	.section	.text.unlikely
.LCOLDE2:
	.text
.LHOTE2:
	.section	.rodata.cst4,"aM",@progbits,4
	.align 4
.LC1:
	.long	1112014848
	.ident	"GCC: (Ubuntu 5.4.0-6ubuntu1~16.04.11) 5.4.0 20160609"
	.section	.note.GNU-stack,"",@progbits

	.file	"qsorts.c"
	.text
	.p2align 4,,15
	.globl	swap
	.type	swap, @function
swap:
.LFB2:
	.cfi_startproc
	movl	(%rdi), %eax
	movl	(%rsi), %edx
	movl	%edx, (%rdi)
	movl	%eax, (%rsi)
	ret
	.cfi_endproc
.LFE2:
	.size	swap, .-swap
	.p2align 4,,15
	.globl	partition_1
	.type	partition_1, @function
partition_1:
.LFB3:
	.cfi_startproc
	movl	%esi, %eax
	movl	(%rdi), %edx
	xorl	%r10d, %r10d
	shrl	$31, %eax
	addl	%esi, %eax
	sarl	%eax
	cltq
	leaq	(%rdi,%rax,4), %rax
	movl	(%rax), %ecx
	movl	%ecx, (%rdi)
	movl	%edx, (%rax)
	movl	%esi, %eax
	movl	(%rdi), %r9d
	.p2align 4,,10
	.p2align 3
.L4:
	cmpl	%esi, %r10d
	jge	.L5
	addl	$1, %r10d
	movslq	%r10d, %rdx
	cmpl	%r9d, (%rdi,%rdx,4)
	jl	.L4
.L5:
	movslq	%eax, %rdx
	leaq	-4(%rdi,%rdx,4), %rdx
	.p2align 4,,10
	.p2align 3
.L7:
	movq	%rdx, %r8
	subq	$4, %rdx
	movl	4(%rdx), %ecx
	subl	$1, %eax
	cmpl	%r9d, %ecx
	jg	.L7
	cmpl	%r10d, %eax
	jle	.L8
	movslq	%r10d, %rdx
	leaq	(%rdi,%rdx,4), %rdx
	movl	(%rdx), %r11d
	movl	%ecx, (%rdx)
	movl	%r11d, (%r8)
	jmp	.L4
	.p2align 4,,10
	.p2align 3
.L8:
	movl	(%rdi), %edx
	movl	%ecx, (%rdi)
	movl	%edx, (%r8)
	ret
	.cfi_endproc
.LFE3:
	.size	partition_1, .-partition_1
	.p2align 4,,15
	.globl	qsort295_1
	.type	qsort295_1, @function
qsort295_1:
.LFB0:
	.cfi_startproc
	cmpl	$1, %esi
	jle	.L15
	pushq	%r12
	.cfi_def_cfa_offset 16
	.cfi_offset 12, -16
	movq	%rdi, %r12
	pushq	%rbp
	.cfi_def_cfa_offset 24
	.cfi_offset 6, -24
	pushq	%rbx
	.cfi_def_cfa_offset 32
	.cfi_offset 3, -32
	movl	%esi, %ebx
.L12:
	movl	%ebx, %esi
	movq	%r12, %rdi
	call	partition_1
	movslq	%eax, %rbp
	subl	%ebp, %ebx
	movl	%ebp, %esi
	leaq	4(%r12,%rbp,4), %r12
	subl	$1, %ebx
	call	qsort295_1
	cmpl	$1, %ebx
	jg	.L12
	popq	%rbx
	.cfi_def_cfa_offset 24
	popq	%rbp
	.cfi_def_cfa_offset 16
	popq	%r12
	.cfi_def_cfa_offset 8
	ret
	.p2align 4,,10
	.p2align 3
.L15:
	.cfi_restore 3
	.cfi_restore 6
	.cfi_restore 12
	rep ret
	.cfi_endproc
.LFE0:
	.size	qsort295_1, .-qsort295_1
	.p2align 4,,15
	.globl	partition_2
	.type	partition_2, @function
partition_2:
.LFB4:
	.cfi_startproc
	leal	-1(%rsi), %eax
	pushq	%rbx
	.cfi_def_cfa_offset 16
	.cfi_offset 3, -16
	movl	(%rdi), %r8d
	movslq	%eax, %rdx
	testl	%eax, %eax
	leaq	(%rdi,%rdx,4), %rbx
	movl	(%rbx), %r9d
	jle	.L23
	leal	-2(%rsi), %eax
	movq	%rdi, %rcx
	movl	%r8d, %r10d
	leaq	4(%rdi,%rax,4), %r11
	xorl	%eax, %eax
	jmp	.L22
	.p2align 4,,10
	.p2align 3
.L21:
	movl	(%rcx), %r8d
.L22:
	movslq	%eax, %rdx
	salq	$2, %rdx
	cmpl	%r8d, %r9d
	leaq	(%rdi,%rdx), %rsi
	jl	.L20
	movl	%r8d, (%rsi)
	leaq	4(%rdi,%rdx), %rsi
	movl	%r10d, (%rcx)
	addl	$1, %eax
	movl	(%rsi), %r10d
.L20:
	addq	$4, %rcx
	cmpq	%r11, %rcx
	jne	.L21
	movl	(%rbx), %r9d
	movl	%r9d, (%rsi)
	movl	%r10d, (%rbx)
	popq	%rbx
	.cfi_remember_state
	.cfi_def_cfa_offset 8
	ret
	.p2align 4,,10
	.p2align 3
.L23:
	.cfi_restore_state
	movl	%r8d, %r10d
	movq	%rdi, %rsi
	xorl	%eax, %eax
	movl	%r9d, (%rsi)
	movl	%r10d, (%rbx)
	popq	%rbx
	.cfi_def_cfa_offset 8
	ret
	.cfi_endproc
.LFE4:
	.size	partition_2, .-partition_2
	.p2align 4,,15
	.globl	qsort295_2
	.type	qsort295_2, @function
qsort295_2:
.LFB1:
	.cfi_startproc
	cmpl	$1, %esi
	jle	.L30
	pushq	%r12
	.cfi_def_cfa_offset 16
	.cfi_offset 12, -16
	movq	%rdi, %r12
	pushq	%rbp
	.cfi_def_cfa_offset 24
	.cfi_offset 6, -24
	pushq	%rbx
	.cfi_def_cfa_offset 32
	.cfi_offset 3, -32
	movl	%esi, %ebx
.L27:
	movl	%ebx, %esi
	movq	%r12, %rdi
	call	partition_2
	movslq	%eax, %rbp
	subl	%ebp, %ebx
	movl	%ebp, %esi
	leaq	4(%r12,%rbp,4), %r12
	subl	$1, %ebx
	call	qsort295_2
	cmpl	$1, %ebx
	jg	.L27
	popq	%rbx
	.cfi_def_cfa_offset 24
	popq	%rbp
	.cfi_def_cfa_offset 16
	popq	%r12
	.cfi_def_cfa_offset 8
	ret
	.p2align 4,,10
	.p2align 3
.L30:
	.cfi_restore 3
	.cfi_restore 6
	.cfi_restore 12
	rep ret
	.cfi_endproc
.LFE1:
	.size	qsort295_2, .-qsort295_2
	.ident	"GCC: (Ubuntu 7.4.0-1ubuntu1~16.04~ppa1) 7.4.0"
	.section	.note.GNU-stack,"",@progbits

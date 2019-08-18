	.file	"main.c"
	.text
	.section	.rodata.str1.1,"aMS",@progbits,1
.LC0:
	.string	"Error:  sum mismatch"
	.section	.rodata.str1.8,"aMS",@progbits,1
	.align 8
.LC1:
	.string	"Sample %d completed in %d cycles.\n"
	.section	.rodata.str1.1
.LC2:
	.string	"Average of %ld cycles.\n"
	.section	.text.startup,"ax",@progbits
	.p2align 4,,15
	.globl	main
	.type	main, @function
main:
.LFB38:
	.cfi_startproc
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	pushq	%rbx
	.cfi_def_cfa_offset 24
	.cfi_offset 3, -24
	xorl	%edi, %edi
	xorl	%eax, %eax
	movl	$A, %ebp
	movl	$A+400, %ebx
	subq	$8, %rsp
	.cfi_def_cfa_offset 32
	call	time
	movl	%eax, %edi
	call	srand
	movl	$0, Q(%rip)
	.p2align 4,,10
	.p2align 3
.L3:
	call	rand
	cltd
	shrl	$22, %edx
	addl	%edx, %eax
	andl	$1023, %eax
	subl	%edx, %eax
	subl	$512, %eax
	testl	%eax, %eax
	movl	%eax, 0(%rbp)
	jle	.L2
	addl	%eax, Q(%rip)
.L2:
	addq	$4, %rbp
	cmpq	%rbp, %rbx
	jne	.L3
	xorl	%ebp, %ebp
	.p2align 4,,10
	.p2align 3
.L6:
#APP
# 40 "main.c" 1
	cpuid
	rdtscp
	movl %eax, %esi
	
# 0 "" 2
#NO_APP
	movl	$A, %edi
	movl	%esi, start_time(%rip)
	movl	$100, %esi
	call	sum_plus
	movl	%eax, %edi
	movl	%eax, P(%rip)
#APP
# 44 "main.c" 1
	cpuid
	rdtscp
	movl %eax, %esi
	
# 0 "" 2
#NO_APP
	movl	%esi, end_time(%rip)
	subl	start_time(%rip), %esi
	movslq	%ebp, %rax
	cmpl	Q(%rip), %edi
	movl	%esi, cycles(,%rax,4)
	jne	.L13
	xorl	%eax, %eax
	cmpl	$3999, %esi
	setg	%al
	subl	%eax, %ebp
	addl	$1, %ebp
	cmpl	$20, %ebp
	jne	.L6
	movq	$0, total(%rip)
	movl	$1, %ebx
	.p2align 4,,10
	.p2align 3
.L7:
	movl	cycles-4(,%rbx,4), %ecx
	movl	%ebx, %edx
	xorl	%eax, %eax
	movl	$.LC1, %esi
	movl	$1, %edi
	call	__printf_chk
	movslq	cycles-4(,%rbx,4), %rax
	addq	$1, %rbx
	addq	total(%rip), %rax
	cmpq	$21, %rbx
	movq	%rax, total(%rip)
	jne	.L7
	movl	$20, %ecx
	cqto
	movl	$.LC2, %esi
	idivq	%rcx
	popq	%rcx
	.cfi_remember_state
	.cfi_def_cfa_offset 24
	popq	%rbx
	.cfi_def_cfa_offset 16
	popq	%rbp
	.cfi_def_cfa_offset 8
	movl	$1, %edi
	movq	%rax, %rdx
	xorl	%eax, %eax
	jmp	__printf_chk
.L13:
	.cfi_restore_state
	popq	%rsi
	.cfi_def_cfa_offset 24
	popq	%rbx
	.cfi_def_cfa_offset 16
	popq	%rbp
	.cfi_def_cfa_offset 8
	movl	$.LC0, %edi
	jmp	perror
	.cfi_endproc
.LFE38:
	.size	main, .-main
	.globl	end_time
	.data
	.align 4
	.type	end_time, @object
	.size	end_time, 4
end_time:
	.long	125
	.globl	start_time
	.align 4
	.type	start_time, @object
	.size	start_time, 4
start_time:
	.long	150
	.comm	total,8,8
	.comm	cycles,80,32
	.comm	Q,4,4
	.comm	P,4,4
	.comm	A,400,32
	.ident	"GCC: (Ubuntu 7.4.0-1ubuntu1~16.04~ppa1) 7.4.0"
	.section	.note.GNU-stack,"",@progbits

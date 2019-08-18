	.file	"main.c"
	.section	.rodata.str1.1,"aMS",@progbits,1
.LC0:
	.string	"%s %f"
.LC1:
	.string	"Rank"
.LC2:
	.string	"Grade"
.LC3:
	.string	"Name"
.LC4:
	.string	"%15s  %5s  %4s\n"
.LC5:
	.string	"----"
.LC6:
	.string	"-----"
.LC7:
	.string	"----------"
.LC8:
	.string	"%15s  %2.2f  %3d\n"
.LC9:
	.string	"Number who passed: %d/%d\n"
.LC10:
	.string	"  Passing average: %2.2f\n"
.LC11:
	.string	"  Overall average: %2.2f\n"
	.section	.rodata.str1.8,"aMS",@progbits,1
	.align 8
.LC12:
	.string	"Sample %d completed in %d cycles.\n"
	.section	.rodata.str1.1
.LC13:
	.string	"Average of %ld cycles.\n"
	.section	.text.unlikely,"ax",@progbits
.LCOLDB14:
	.section	.text.startup,"ax",@progbits
.LHOTB14:
	.p2align 4,,15
	.globl	main
	.type	main, @function
main:
.LFB47:
	.cfi_startproc
	pushq	%r12
	.cfi_def_cfa_offset 16
	.cfi_offset 12, -16
	pushq	%rbp
	.cfi_def_cfa_offset 24
	.cfi_offset 6, -24
	movl	$names, %ebp
	pushq	%rbx
	.cfi_def_cfa_offset 32
	.cfi_offset 3, -32
	movl	$grades, %r12d
	movq	%rbp, %rbx
	.p2align 4,,10
	.p2align 3
.L2:
	movq	stdin(%rip), %rdx
	movl	$1024, %esi
	movl	$buf, %edi
	call	fgets
	movq	%r12, %rcx
	movq	%rax, nul(%rip)
	movq	%rbx, %rdx
	xorl	%eax, %eax
	movl	$.LC0, %esi
	movl	$buf, %edi
	addq	$20, %rbx
	addq	$4, %r12
	call	__isoc99_sscanf
	cmpq	$names+600, %rbx
	jne	.L2
	xorl	%r12d, %r12d
	.p2align 4,,10
	.p2align 3
.L4:
#APP
# 35 "main.c" 1
	cpuid
	rdtscp
	movl %eax, %esi
	
# 0 "" 2
#NO_APP
	movl	$num_passed, %r9d
	movl	%esi, start_time(%rip)
	movl	$passing_avg, %r8d
	movl	$avg, %ecx
	movl	$ranks, %edx
	movl	$30, %esi
	movl	$grades, %edi
	call	compute_ranks
#APP
# 46 "main.c" 1
	cpuid
	rdtscp
	movl %eax, %esi
	
# 0 "" 2
#NO_APP
	movl	%esi, end_time(%rip)
	subl	start_time(%rip), %esi
	movslq	%r12d, %rax
	movl	%esi, cycles(,%rax,4)
	xorl	%eax, %eax
	cmpl	$20000, %esi
	setge	%al
	subl	%eax, %r12d
	addl	$1, %r12d
	cmpl	$19, %r12d
	jle	.L4
	movl	$.LC1, %r8d
	movl	$.LC2, %ecx
	movl	$.LC3, %edx
	movl	$.LC4, %esi
	movl	$1, %edi
	xorl	%eax, %eax
	call	__printf_chk
	xorl	%ebx, %ebx
	movl	$.LC5, %r8d
	movl	$.LC6, %ecx
	movl	$.LC7, %edx
	movl	$.LC4, %esi
	movl	$1, %edi
	xorl	%eax, %eax
	call	__printf_chk
	.p2align 4,,10
	.p2align 3
.L5:
	pxor	%xmm0, %xmm0
	movl	ranks(%rbx), %ecx
	movq	%rbp, %rdx
	movl	$.LC8, %esi
	movl	$1, %edi
	movl	$1, %eax
	addq	$4, %rbx
	addq	$20, %rbp
	cvtss2sd	grades-4(%rbx), %xmm0
	call	__printf_chk
	cmpq	$120, %rbx
	jne	.L5
	movq	stdout(%rip), %rsi
	movl	$10, %edi
	movl	$cycles+4, %ebp
	movl	$1, %ebx
	call	_IO_putc
	movl	num_passed(%rip), %edx
	movl	$30, %ecx
	movl	$.LC9, %esi
	movl	$1, %edi
	xorl	%eax, %eax
	call	__printf_chk
	pxor	%xmm0, %xmm0
	movl	$.LC10, %esi
	movl	$1, %edi
	movl	$1, %eax
	cvtss2sd	passing_avg(%rip), %xmm0
	call	__printf_chk
	pxor	%xmm0, %xmm0
	movl	$.LC11, %esi
	movl	$1, %edi
	movl	$1, %eax
	cvtss2sd	avg(%rip), %xmm0
	call	__printf_chk
	movq	stdout(%rip), %rsi
	movl	$10, %edi
	call	_IO_putc
	movl	cycles(%rip), %ecx
	movl	$1, %edx
	movl	$.LC12, %esi
	movl	$1, %edi
	xorl	%eax, %eax
	movq	$0, total(%rip)
	call	__printf_chk
	.p2align 4,,10
	.p2align 3
.L7:
	movl	0(%rbp), %ecx
	addl	$1, %ebx
	xorl	%eax, %eax
	movl	%ebx, %edx
	movl	$.LC12, %esi
	movl	$1, %edi
	call	__printf_chk
	leal	-1(%rbx), %eax
	cmpl	$9, %eax
	jbe	.L6
	movslq	0(%rbp), %rax
	addq	%rax, total(%rip)
.L6:
	addq	$4, %rbp
	cmpl	$20, %ebx
	jne	.L7
	movq	total(%rip), %rcx
	movabsq	$7378697629483820647, %rdx
	movl	$.LC13, %esi
	popq	%rbx
	.cfi_def_cfa_offset 24
	popq	%rbp
	.cfi_def_cfa_offset 16
	movq	%rcx, %rax
	sarq	$63, %rcx
	movl	$1, %edi
	imulq	%rdx
	xorl	%eax, %eax
	popq	%r12
	.cfi_def_cfa_offset 8
	sarq	$2, %rdx
	subq	%rcx, %rdx
	jmp	__printf_chk
	.cfi_endproc
.LFE47:
	.size	main, .-main
	.section	.text.unlikely
.LCOLDE14:
	.section	.text.startup
.LHOTE14:
	.comm	nul,8,8
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
	.comm	num_passed,4,4
	.comm	passing_avg,4,4
	.comm	avg,4,4
	.comm	ranks,120,32
	.comm	grades,120,32
	.comm	names,600,32
	.comm	buf,1024,32
	.ident	"GCC: (Ubuntu 5.4.0-6ubuntu1~16.04.11) 5.4.0 20160609"
	.section	.note.GNU-stack,"",@progbits

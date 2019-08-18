.globl sum_float


# var map:
#   %xmm0:  result and return value 
#   %xmm1:  x
#   %xmm2:  y
#   %rdi:   F[n] (base pointer)
#   %rsi:   n
#   %rbp:   end
#   %rdx:   head of queue
#   %rcx:   base of queue
#   %r8 :   itr to n 
#   %r9 :   address 


sum_float:
push    %rbp #backup
xorps   %xmm0,    %xmm0 

movq    %rsp,     %rdx              # head of queue -> %rdx
movq    %rsp,     %rcx              # base of queue -> %rcx
leaq    (%rdi,%rsi, 4), %rbp        # endptr <- M[F] + M[(n*4)]
movq    %rdi,     %r9              
movq    $1,       %r8

top: # of the loop 
cmp     %r8,      %rsi 
jl      cleanUp
                                    # obtain x value
cmp     %r9,     %rbp              # if F[] is empty
jle     Qx

movss   (%r9),   %xmm1             # x is head(F), by default
cmp     %rsp,     %rdx              # queue not empty ?
jl      Fx

ucomiss (%rdx),   %xmm1             # head(F) - head(Q) > 0 ?
jle     Fx  #jle not je


#           Qx    
#       /        \
# XOR <            >  xMain
#       \        /
#           Fx  
Qx:                               
movss   (%rdx),   %xmm1             # x = head of Q
leaq    -4(%rdx), %rdx              # new head of Q
jmp     xMain

Fx:                               
leaq    4(%r9),  %r9           # x = head(F)... which we did in xMain 

xMain:                           # obtain y value
cmp     %r9,     %rbp            # F[] is empty      ? jmp Qj : nexLine
jle     Qy
movss   (%r9),   %xmm2           # y <- head(F)
cmp     %rdx  ,   %rsp           # queue not empty   ? jmp Fy : nextLine
jge     Fy #jne works to 
ucomiss (%rdx),   %xmm2          # head(F) < head(Q) ? jmp Fy : nextLine
jge     Fy
 
Qy:                              
movss   (%rdx),   %xmm2          # y =head(Q)
leaq    -4(%rdx), %rdx
jmp     yMain

Fy:  #kind of redundant                             
leaq    4(%r9),  %r9            # y = head(F)... which we did in xMain

yMain:  #didnt use push, since pop-ing will be messy. RCX is a clean RSP
leaq    -4(%rsp), %rsp           # add space to queue
addss   %xmm2,    %xmm1          # x <- x + y
movss   %xmm1,    (%rsp)         # enqueue(x+y)
incq    %r8
jmp     top #of the loop                      

cleanUp:
movss   (%rsp),   %xmm0          # Pop            -> %xmm0
movq    %rcx,     %rsp           # base of queque -> rsp
                                 # RCX is a clean RSP
return:
pop     %rbp                     # restore/prevent seg fault

ret
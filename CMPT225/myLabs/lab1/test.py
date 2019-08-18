#!/usr/bin/env python2.7
import subprocess

passed = 0

# Yes, these should be in a for loop.
# Run test 1
print "Running test 1... "
subprocess.call("./hello_world < 1.in > 1.out",shell=True)
rt = subprocess.call("diff -b 1.out 1.gt",shell=True);
if rt == 0:
    passed += 1
    print "passed"
else:
    print "failed"

# Run test 2
print "Running test 2... "
subprocess.call("./hello_world < 2.in > 2.out",shell=True)
rt = subprocess.call("diff -b 2.out 2.gt",shell=True);
if rt == 0:
    passed += 1
    print "passed"
else:
    print "failed"

# Run test 3
print "Running test 2... "
subprocess.call("./hello_world < 3.in > 3.out",shell=True)
rt = subprocess.call("diff -b 3.out 3.gt",shell=True);
if rt == 0:
    passed += 1
    print "passed"
else:
    print "failed"

print "Passed " + str(passed) + " of 3 tests"

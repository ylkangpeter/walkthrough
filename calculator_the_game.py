# -*- coding: utf-8 -*-

import itertools
from decimal import Decimal


def gen(condition, steps):
    counter = 0

    tmp = []
    for i in xrange(0, steps - len(condition) + 1):
        for j in xrange(0, len(condition)):
            tmp.append(condition[j])

    allSteps = list(itertools.combinations(tmp, steps - len(condition)))
    re = []
    for i in xrange(0, len(allSteps)):
        tmp = list(allSteps[i])
        for j in xrange(0, len(condition)):
            tmp.append(condition[j])
        re.append(tmp)
    tmpRe = set()
    rr = []
    for i in xrange(0, len(re)):
        v = list(re[i])
        v.sort()
        m = "_".join(v)
        if not tmpRe.__contains__(m):
            tmpRe.add(m)
            rr.append(v)

    allSteps = []
    for i in xrange(0, len(rr)):
        allSteps.extend(list(itertools.permutations(rr[i], steps)))
    return allSteps


def doBinaryMath(initVal, operation):
    if operation.__contains__("=>"):
        vals = operation.split("=>")
        tmp = str(initVal)
        return Decimal(tmp.replace(vals[0], vals[1]))
    else:
        sign = operation[0]
        t = 0

        if sign == "-":
            t = initVal + Decimal(operation)
        elif sign == "m":
            tmp = str(initVal)
            t = Decimal(tmp + tmp[::-1])
        elif sign == "+":
            if operation == "+":
                t = initVal * (-1)
            else:
                t = initVal + Decimal(operation)
        elif sign == "*":
            t = initVal * Decimal(operation[1:])
        elif sign == "/":
            t = initVal / Decimal(operation[1:])
        elif sign == "r":
            t = Decimal(str(initVal)[::-1])
        elif sign == "<":
            t = int(initVal / 10)
        else:
            if initVal >= 0:
                t = initVal * 10 + Decimal(operation)
            else:
                t = initVal * 10 - Decimal(operation)
    return t


# def test(comb, initVal):
#     for j in xrange(0, len(comb)):
#         initVal = doBinaryMath(initVal, comb[j])
#     return initVal


def calc(combinations, result, initVal):
    for i in xrange(0, len(combinations)):
        val = Decimal(initVal)
        tmps = list(combinations[i])

        for j in xrange(0, len(tmps)):
            if tmps[j].startswith("["):
                v = tmps[j].split(" ")
                for m in xrange(0, len(tmps)):
                    tmps[m] = decorate(tmps[m], v[0][1], v[1]);
                else:
                    val = doBinaryMath(val, tmps[j])
        # print "%s %d" % (combinations[i], val)
        if val == result:
            print "result: %s" % str(combinations[i])


def decorate(raw, oper, addup):
    if raw == "-" or raw == "m" or raw == "<" or raw == "r" or raw == "+" or raw.__contains__("=>") or raw[0] == "[":
        return;
    elif raw.isdigit():
        return eval(raw + oper + addup)
    else:
        return raw[0] + str(eval(raw[1:] + oper + addup))


        # test(['+2', '1', '[+ 3'], 0)


initVal = 0
condition = ['+2', '1', '[+ 3']
steps = 5
result = 28

combinations = gen(condition, steps)
# print combinations

print "input: \n\tinitVal: %d\n\tsteps: %d\n\texpectation: %d\n\tconditions: %s\n\n" % (
    initVal, steps, result, condition)

calc(combinations, result, initVal)

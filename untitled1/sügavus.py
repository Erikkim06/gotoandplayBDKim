def sügavus(list):
    if list == []:
        return 0
    for a in list:
        ajutine = sügavus(a) + 1

    return ajutine

print(sügavus([[],[[[[[[[[[[]]]]]]]]]]]))

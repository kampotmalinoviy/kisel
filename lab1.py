import math

def f(x):
    return x**2

def b(x,y,f):
    if y == 0:
        return 0
    elif x == 0:
        return (f(x) ** 2 + y)**3
    elif x/y < 0:
        return math.log(abs(f(x)/y)) + (f(x) ** 2 +y)**3
    else:
        return math.log(f(x)) + (f(x) ** 2 +y)**3

x = int(input("Enter the value of x: "))
y = int(input("Enter the value of y: "))

result = b(x,y,f)
print("Result: ", result)

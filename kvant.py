import math
import matplotlib.pyplot as plt

def r100(r, r1):
    return 4 / pow(r1, 3) * math.exp(-2 * r / r1) * pow(r, 2)

def r200(r, r1):
    return 1 / (2 * pow(r1, 3)) * pow((1 - (r / (2 * r1))), 2) * math.exp(r / r1) * pow(r, 2)

r1 = 0.53

print("---------r100---------")
print(" r       r100")
r100_x = []
r100_y = []
for i in [x * 0.1 for x in range(21)]:  # r от 0 до 2 с шагом 0.1
    result = r100(i, r1)
    r100_x.append(i)
    r100_y.append(result)
    print(f" {i:.1f}       {result:.5f}")

r200_x_case1, r200_y_case1 = [], []
print("---------r200---------")
print(" r       r200")
print("case 1")
for i in [x * 0.1 for x in range(15)]:  # r от 0 до 1.5 с шагом 0.1
    result = r200(i, r1)
    r200_x_case1.append(i)
    r200_y_case1.append(result)
    print(f" {i:.1f}       {result:.5f}")

r200_x_case2, r200_y_case2 = [], []
print("case 2")
for i in [1.5 + x * 0.25 for x in range(9)]:  # r от 1.5 до 3.5 с шагом 0.25
    result = r200(i, r1)
    r200_x_case2.append(i)
    r200_y_case2.append(result)
    print(f" {i:.2f}       {result:.5f}")

r200_x_case3, r200_y_case3 = [], []
print("case 3")
for i in [3.5 + x * 0.5 for x in range(7)]:  # r от 3.5 до 7 с шагом 0.5
    result = r200(i, r1)
    r200_x_case3.append(i)
    r200_y_case3.append(result)
    print(f" {i:.2f}       {result:.5f}")


plt.figure(figsize=(10, 8))

# График r100
plt.subplot(2, 2, 1)
plt.plot(r100_x, r100_y, label='r100(r)', marker='o')
plt.xlabel('r')
plt.ylabel('r100')
plt.title('График r100(r)')
plt.legend()
plt.grid()

# График r200 (Case 1)
plt.subplot(2, 2, 2)
plt.plot(r200_x_case1, r200_y_case1, label='r200(r) Case 1', marker='o', color='orange')
plt.xlabel('r')
plt.ylabel('r200')
plt.title('График r200(r) Case 1')
plt.legend()
plt.grid()

# График r200 (Case 2)
plt.subplot(2, 2, 3)
plt.plot(r200_x_case2, r200_y_case2, label='r200(r) Case 2', marker='o', color='green')
plt.xlabel('r')
plt.ylabel('r200')
plt.title('График r200(r) Case 2')
plt.legend()
plt.grid()

# График r200 (Case 3)
plt.subplot(2, 2, 4)
plt.plot(r200_x_case3, r200_y_case3, label='r200(r) Case 3', marker='o', color='red')
plt.xlabel('r')
plt.ylabel('r200')
plt.title('График r200(r) Case 3')
plt.legend()
plt.grid()

plt.tight_layout()
plt.show()


import math

def r100(r, r1):
    return 4 / pow(r1, 3) * math.exp(-2 * r / r1) * pow(r, 2)

def r200(r, r1):
    return 1 / (2 * pow(r1, 3)) * pow((1 - (r / (2 * r1))), 2) * math.exp(-r / r1) * pow(r, 2)

r1 = 0.53

# Вывод таблицы для r100
print("--------- Таблица r100 ---------")
print("{:<10} {:<15}".format("r", "r100"))
print("-" * 25)
for i in [x * 0.1 for x in range(21)]:  # r от 0 до 2 с шагом 0.1
    result = r100(i, r1)
    print("{:<10.1f} {:<15.5f}".format(i, result))

# Вывод таблицы для r200
print("\n--------- Таблица r200 ---------")

# Case 1
print("Case 1 (r от 0 до 1.5 с шагом 0.1):")
print("{:<10} {:<15}".format("r", "r200"))
print("-" * 25)
for i in [x * 0.1 for x in range(15)]:  # r от 0 до 1.5 с шагом 0.1
    result = r200(i, r1)
    print("{:<10.1f} {:<15.5f}".format(i, result))

# Case 2
print("\nCase 2 (r от 1.5 до 3.5 с шагом 0.25):")
print("{:<10} {:<15}".format("r", "r200"))
print("-" * 25)
for i in [1.5 + x * 0.25 for x in range(9)]:  # r от 1.5 до 3.5 с шагом 0.25
    result = r200(i, r1)
    print("{:<10.2f} {:<15.5f}".format(i, result))

# Case 3
print("\nCase 3 (r от 3.5 до 7 с шагом 0.5):")
print("{:<10} {:<15}".format("r", "r200"))
print("-" * 25)
for i in [3.5 + x * 0.5 for x in range(7)]:  # r от 3.5 до 7 с шагом 0.5
    result = r200(i, r1)
    print("{:<10.2f} {:<15.5f}".format(i, result))


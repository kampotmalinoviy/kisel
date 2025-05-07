#1. Напишите программу, которая находит наибольший элемент в списке чисел.
list = input ("Enter the elements of the list: ").split()
max = list[0]
for i in range(len(list)):
    if list[i] > max:
        max = list[i]
print("The maximum element in the list is ", max)

#2. Дан кортеж чисел. Найдите произведение всех элементов.
tup = input("Enter the elements of the tuple: ").split()
tup = [int(i) for i in tup]
result = 1
for i in range(len(tup)):
    result *= tup[i]
print("The composition of the elements: ", result)

#3. Создайте программу, которая считает количество вхождений каждого слова в строке.
string = input("Enter the string: ")
words = string.split()
word_count = {}
for word in words:
    word_count[word] = word_count.get(word, 0) + 1
for word, count in word_count.items():
    print(f"{word} -> {count}")

#4. Дан список чисел. Удалите все отрицательные числа.
numbers = list(map(int, input("Enter the list of numbers: ").split()))
for num in numbers [:]:
    if num < 0:
        numbers.remove(num)
print("Result: ", numbers)

#5. Напишите программу, которая находит сумму цифр введённого числа.
number = int(input("Enter a number: "))
sum = 0
while number > 0:
    sum += number % 10
    number /=10

print("The sum is ", int(sum))

#6. Дан список строк. Отсортируйте его по возрастанию длины строк.
words = input("Enter the list of the words: ").split()
sorted_words=sorted(words, key=len)
print(sorted_words)

#7. Создайте словарь, где ключами будут числа от 1 до N, а значениями — их кубы.
dictionary = {}
N = int(input("N = "))

for i in range(1,N+1):
    dictionary[i] = i ** 3

print("Dictionary: ", dictionary)

#8. Напишите программу, которая выводит таблицу умножения на число N.
multiplication_table = []
N = int(input("N = "))
for i in range(1, 11):
    multiplication_table.append(i*N)

for i in range(0, 10):
    print("{}x{} = {}".format(i+1, N, multiplication_table[i]))


#9. Дан список чисел. Выведите элементы списка в обратном порядке.
numbers_list = input("List: ").split()
numbers_list = [int(num) for num in numbers_list]
for item in reversed(numbers_list):
    print(item,  end = ' ')

#10. Дан словарь с продуктами и их ценами. Найдите самый дешёвый продукт.
products = {}
N = int(input("Enter the number of products: "))
for _ in range(0, N):
     product = input("Enter the product name: ")
     price = int(input("Enter the price of " + product + ": 953942"))
     products[product] = price

print("Product dictionary\n", products)
cheapest_product = min(products, key = products.get)
print("The cheapest item: ", cheapest_product, "--> ", products[cheapest_product])
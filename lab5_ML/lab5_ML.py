import numpy as np
import tensorflow as tf
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Dense
from sklearn.model_selection import train_test_split
import matplotlib.pyplot as plt

# Загрузка данных
X = np.loadtxt('dataIn.txt', dtype=int)
Y = np.loadtxt('dataOut.txt', dtype=int)

# Разделение на обучающую и тестовую выборки
X_train, X_test, Y_train, Y_test = train_test_split(X, Y, test_size=0.2, random_state=42)

# Создание модели
model = Sequential([
    Dense(12, input_dim=12, activation='logsig'),  # Логистическая сигмоида
    Dense(2, activation='softmax')  # Выходной слой для двух классов
])

# Компиляция модели
model.compile(loss='categorical_crossentropy', optimizer='adam', metrics=['accuracy'])

# Обучение модели
history = model.fit(X_train, Y_train, epochs=50, batch_size=10, validation_data=(X_test, Y_test))

# Оценка модели
loss, accuracy = model.evaluate(X_test, Y_test)
print(f"Точность модели на тестовой выборке: {accuracy:.2f}")

# Визуализация
plt.plot(history.history['accuracy'], label='Accuracy')
plt.plot(history.history['val_accuracy'], label='Validation Accuracy')
plt.xlabel('Epochs')
plt.ylabel('Accuracy')
plt.legend()
plt.show()

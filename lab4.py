import numpy as np
import matplotlib.pyplot as plt
from mpl_toolkits.mplot3d import Axes3D

# Генерация данных
x = np.linspace(-5, 5, 100)
y = np.linspace(-5, 5, 100)
x, y = np.meshgrid(x, y)
z = np.sin(np.sqrt(x**2 + y**2))

# Построение графика
fig = plt.figure(figsize=(10, 8))  # Размера фигуры
ax = fig.add_subplot(111, projection='3d')  # 3D-проекцию
surface = ax.plot_surface(x, y, z, cmap='cool')  # Построение поверхности

# Добавление цветовой шкалы
fig.colorbar(surface, shrink=0.5, aspect=10)

ax.set_title('3D График поверхности')
ax.set_xlabel('X')
ax.set_ylabel('Y')
ax.set_zlabel('Z')

plt.show()
import streamlit as st
import requests
from PIL import Image

FASTAPI_URL = "http://localhost:8000"

with st.expander("Создать новую задачу"):
    with st.form("create_task"):
        title = st.text_input("Название задачи")
        task_id = st.number_input("Task ID", min_value=1)
        completed = st.checkbox("Выполнена")

        if st.form_submit_button("Создать"):
            response = requests.post(
                f"{FASTAPI_URL}/api/tasks",
                json={"id": task_id, "title": title, "completed": completed}
            )
            if response.status_code == 201:
                st.success("Задача создана!")
            else:
                st.error(f"Ошибка: {response.json()}")

with st.expander("Список задач"):
    status_filter = st.selectbox("Фильтр по статусу", ["Все", "Выполненные", "Невыполненные"])

    params = {}
    if status_filter == "Выполненные":
        params["completed"] = True
    elif status_filter == "Невыполненные":
        params["completed"] = False

    if st.button("Обновить список"):
        response = requests.get(f"{FASTAPI_URL}/api/tasks", params=params)
        if response.status_code == 200:
            tasks = response.json()
            for task in tasks:
                st.write(f"ID: {task['id']} | {task['title']} | Статус: {'✅' if task['completed'] else '❌'}")
        else:
            st.error("Ошибка загрузки задач")

with st.expander("Управление задачами"):
    task_id = st.number_input("ID задачи", min_value=1)

    col1, col2 = st.columns(2)
    with col1:
        if st.button("Обновить статус"):
            response = requests.put(
                f"{FASTAPI_URL}/api/tasks/{task_id}",
                json={"completed": True}
            )
            st.toast("Статус обновлен" if response.ok else "Ошибка")

    with col2:
        if st.button("Удалить"):
            response = requests.delete(f"{FASTAPI_URL}/api/tasks/{task_id}")
            st.toast("Задача удалена" if response.ok else "Ошибка")


with st.expander("Графики"):
    graphs = {
        "График двух функций": "graph1.png",
        "Точечная диаграмма": "graph2.png",
        "Круговая диаграмма": "graph3.png",
        "Тепловая карта": "graph4.png",
        "Столбчатая диаграмма": "graph5.png",
        "График плоскости": "graph6.png"
    }

    selected_graph = st.selectbox("Выберите график", list(graphs.keys()))

    image_path = "D:/python_projects/Python_labs/lab5/" +graphs.get(selected_graph)
    if image_path:
        image = Image.open(image_path)
        st.image(image, caption=selected_graph, use_container_width=True)
import streamlit as st
import os
import requests
from PIL import Image

FASTAPI_URL = "http://localhost:8000"

graphs = ["graph1.png", "graph2.png", "graph3.png",
          "graph4.png", "graph5.png", "graph6.png"]

for graph in graphs:
    image = Image.open(graph)
    st.image(image,
             caption=graph,
             use_column_width=True,
             output_format="PNG")

st.title("Task Manager")

with st.expander("Создать новую задачу"):
    with st.form("create_task"):
        title = st.text_input("Название задачи")
        user_id = st.number_input("User ID", min_value=1)
        completed = st.checkbox("Выполнена")

        if st.form_submit_button("Создать"):
            response = requests.post(
                f"{FASTAPI_URL}/api/v1/tasks/",
                json={"title": title, "user_id": user_id, "completed": completed}
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
        response = requests.get(f"{FASTAPI_URL}/api/v1/tasks/", params=params)
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
                f"{FASTAPI_URL}/api/v1/tasks/{task_id}",
                json={"completed": True}
            )
            st.toast("Статус обновлен" if response.ok else "Ошибка")

    with col2:
        if st.button("Удалить"):
            response = requests.delete(f"{FASTAPI_URL}/api/v1/tasks/{task_id}")
            st.toast("Задача удалена" if response.ok else "Ошибка")

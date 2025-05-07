from fastapi import APIRouter, HTTPException, Query, BackgroundTasks
from app.models import Task
from typing import Optional, List

router = APIRouter()

tasks: List[Task] = []


@router.post("/tasks", response_model=dict)
def add_task(task: Task):
    # if any(existing_task.id == task.id for existing_task in tasks):
    #     raise HTTPException(status_code=400, detail="Task with this ID already exists")

    tasks.append(task)

    return {"message": "Task added",
            "task": task.model_dump()}  # Возвращаем всю информацию о задаче

@router.put("/tasks/{task_id}", response_model=Task)
def update_task(task_id: int, completed: bool):
    for task in tasks:
        if task.id == task_id:
            task.completed = completed
            return task
        raise HTTPException(status_code=404, detail="Task not found")


@router.delete("/tasks/{task_id}")
def delete_task(task_id: int):
    global tasks
    tasks = [task for task in tasks if task.id != task_id]
    return {"message": "Task deleted"}


@router.get("/tasks", response_model=List[Task])
def get_tasks(
        completed: Optional[bool] = Query(None, description="Фильтрация по статусу выполнения"),
        limit: int = Query(10, description="Ограничение количества возвращаемых задач")
):
    filtered_tasks = tasks

    if completed is not None:
        filtered_tasks = [task for task in tasks if task.completed == completed]

    return filtered_tasks[:limit]


from fastapi import APIRouter, Depends, HTTPException, status, BackgroundTasks, Query
from sqlalchemy.orm import Session
from typing import List, Optional

from lab5.app.database import get_db
from lab5.app.models.task import Task
from lab5.app.schemas.task import TaskCreate, TaskUpdate, Task
from lab5.app.crud.tasks import get_task, get_tasks, create_task, update_task, delete_task

router = APIRouter(tags=["tasks"])

def send_notification(email: str, message: str):
    logging.info(f"Email sent to {email}: {message}")

@router.post(
    "/",
    response_model=Task,
    status_code=status.HTTP_201_CREATED,
    summary="Создать новую задачу",
    responses={
        201: {"description": "Задача успешно создана"},
        400: {"description": "Некорректные входные данные"},
        422: {"description": "Ошибка валидации данных"}
    }
)
def create_new_task(
    task: TaskCreate,
    background_tasks: BackgroundTasks,
    db: Session = Depends(get_db)
):
    db_task = create_task(db, task.model_dump())
    background_tasks.add_task(
        send_notification,
        email=f"user{task.user_id}@example.com",
        message=f"Создана новая задача: {task.title}"
    )
    return db_task

@router.get(
    "/",
    response_model=List[Task],
    summary="Получить список задач",
    responses={
        200: {"description": "Успешный запрос"},
    }
)
def read_tasks(
    skip: int = Query(0, ge=0, description="Количество пропускаемых задач"),
    limit: int = Query(100, le=500, description="Максимальное количество задач"),
    completed: Optional[bool] = Query(None, description="Фильтр по статусу выполнения"),
    db: Session = Depends(get_db)
):
    return get_tasks(db, skip=skip, limit=limit, completed=completed)

@router.get(
    "/{task_id}",
    response_model=Task,
    summary="Получить задачу по ID",
    responses={
        200: {"description": "Задача найдена"},
        404: {"description": "Задача не найдена"}
    }
)
def read_task(task_id: int, db: Session = Depends(get_db)):
    db_task = get_task(db, task_id)
    if not db_task:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="Task not found"
        )
    return db_task

@router.put(
    "/{task_id}",
    response_model=Task,
    summary="Обновить задачу",
    responses={
        200: {"description": "Задача обновлена"},
        404: {"description": "Задача не найдена"},
        422: {"description": "Ошибка валидации данных"}
    }
)
def update_task(
    task_id: int,
    task: TaskUpdate,
    db: Session = Depends(get_db)
):
    db_task = update_task(db, task_id, task.model_dump(exclude_unset=True))
    if not db_task:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="Task not found"
        )
    return db_task

@router.delete(
    "/{task_id}",
    response_model=Task,
    summary="Удалить задачу",
    responses={
        200: {"description": "Задача удалена"},
        404: {"description": "Задача не найдена"}
    }
)
def delete_task(task_id: int, db: Session = Depends(get_db)):
    db_task = delete_task(db, task_id)
    if not db_task:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="Task not found"
        )
    return db_task
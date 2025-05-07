from pydantic import BaseModel, Field
from typing import Optional

class TaskBase(BaseModel):
    title: str = Field(
        ...,
        min_length=1,
        max_length=100,
        example="Купить молоко",
        description="Название задачи (1-100 символов)"
    )
    completed: bool = Field(
        default=False,
        example=False,
        description="Статус выполнения задачи"
    )

class TaskCreate(TaskBase):
    user_id: int = Field(
        ...,
        example=1,
        description="ID пользователя, создающего задачу"
    )

class TaskUpdate(BaseModel):
    title: Optional[str] = Field(
        None,
        min_length=1,
        max_length=100,
        example="Обновленное название",
        description="Новое название задачи"
    )
    completed: Optional[bool] = Field(
        None,
        example=True,
        description="Новый статус выполнения"
    )

class Task(TaskBase):
    id: int = Field(..., example=1)
    user_id: int = Field(..., example=1)

    class Config:
        from_attributes = True
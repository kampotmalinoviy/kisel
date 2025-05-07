from pydantic import BaseModel, Field

class Task(BaseModel):
    id: int
    title: str
    completed: bool


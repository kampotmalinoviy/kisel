from fastapi import FastAPI
from lab5.app.database import Base, engine
from lab5.app.api import tasks as tasks_router
from lab5.app.models.user import User
from lab5.app.models.task import Task
from lab5.app.api.users import router as users_router

import logging

logging.basicConfig(
    level=logging.INFO,
    format='%(asctime)s - %(name)s - %(levelname)s - %(message)s'
)

app = FastAPI()

Base.metadata.create_all(bind=engine)

app.include_router(
    tasks_router.router,
    prefix="/api/v1/tasks",
    tags=["tasks"]
)
app.include_router(users_router, prefix="/api/v1")

@app.get("/")
def read_root():
    return {"message": "Task Manager API is running"}
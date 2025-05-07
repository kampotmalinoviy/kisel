from sqlalchemy.orm import Session
from lab5.app.models.task import Task

def get_task(db: Session, task_id: int):
    return db.query(Task).filter(Task.id == task_id).first()

def get_tasks(db: Session, skip: int = 0, limit: int = 100, completed: bool = None):
    query = db.query(Task)
    if completed is not None:
        query = query.filter(Task.completed == completed)
    return query.offset(skip).limit(limit).all()

def create_task(db: Session, task: dict):
    db_task = Task(**task)
    db.add(db_task)
    db.commit()
    db.refresh(db_task)
    return db_task

def update_task(db: Session, task_id: int, task: dict):
    db_task = get_task(db, task_id)
    if db_task:
        for key, value in task.items():
            if value is not None:
                setattr(db_task, key, value)
        db.commit()
        db.refresh(db_task)
    return db_task

def delete_task(db: Session, task_id: int):
    db_task = get_task(db, task_id)
    if db_task:
        db.delete(db_task)
        db.commit()
    return db_task
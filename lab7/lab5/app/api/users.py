from fastapi import APIRouter, Depends
from sqlalchemy.orm import Session
from lab5.app.models.user import User
from lab5.app.database import get_db

router = APIRouter(tags=["users"])

@router.get("/users/", response_model=list[dict])
def get_users(db: Session = Depends(get_db)):
    users = db.query(User).all()
    return [
        {"id": user.id, "email": user.email, "name": user.name}
        for user in users
    ]
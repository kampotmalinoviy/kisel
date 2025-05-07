from fastapi import FastAPI, APIRouter
from app.routes import router

app = FastAPI()

app.include_router(router, prefix="/api")

@app.get("/")
def read_root():
    print("Root route was accessed")
    return {"message": "Welcome to the Task Manager API!"}




from fastapi.testclient import TestClient
from Python_labs.lab5.app.main import app

client = TestClient(app)

def test_add_task():
    response = client.post("/api/tasks", json={"id": 1, "title": "Test Task", "completed": False})
    assert response.status_code == 200
    assert response.json()["title"] == "Test Task"
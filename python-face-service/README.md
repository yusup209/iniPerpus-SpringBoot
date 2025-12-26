Face Recognition Microservice

Endpoints:
- POST /enroll: multipart form `student_id` + `image` -> enroll student photo
- POST /match: multipart form `image` -> return best-matching student_id and distance
- GET /students: list enrolled student IDs

Run (recommended in virtualenv):

```bash
pip install -r requirements.txt
uvicorn main:app --host 0.0.0.0 --port 8001
```

Notes:
- `face_recognition` depends on dlib; on Windows follow dlib installation instructions or use WSL.
- Enrollments persisted to `data/encodings.pkl` and images saved under `data/images/`.

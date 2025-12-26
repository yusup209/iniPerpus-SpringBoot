from fastapi import FastAPI, File, UploadFile, Form, HTTPException
from fastapi.responses import JSONResponse
import face_recognition
import numpy as np
import os
import pickle
import threading
from typing import Dict, List
from pathlib import Path

DATA_DIR = Path(__file__).parent / "data"
IMAGES_DIR = DATA_DIR / "images"
ENC_FILE = DATA_DIR / "encodings.pkl"

DATA_DIR.mkdir(parents=True, exist_ok=True)
IMAGES_DIR.mkdir(parents=True, exist_ok=True)

lock = threading.Lock()

app = FastAPI(title="Face Recognition Service")

# Load encodings from disk
def load_encodings() -> Dict[str, List[float]]:
    if ENC_FILE.exists():
        with open(ENC_FILE, "rb") as f:
            return pickle.load(f)
    return {}

# Save encodings to disk
def save_encodings(encs: Dict[str, List[float]]):
    with open(ENC_FILE, "wb") as f:
        pickle.dump(encs, f)

@app.post("/enroll")
async def enroll(student_id: str = Form(...), image: UploadFile = File(...)):
    contents = await image.read()
    try:
        img = face_recognition.load_image_file(contents)
    except Exception:
        # face_recognition.load_image_file accepts file-like or path; handle bytes via numpy
        from PIL import Image
        import io
        pil = Image.open(io.BytesIO(contents)).convert('RGB')
        img = np.array(pil)

    encodings = face_recognition.face_encodings(img)
    if len(encodings) == 0:
        raise HTTPException(status_code=400, detail="No face found in image")
    if len(encodings) > 1:
        raise HTTPException(status_code=400, detail="More than one face found; please provide a single-face image")

    encoding = encodings[0].tolist()

    with lock:
        db = load_encodings()
        db[student_id] = encoding
        save_encodings(db)
        # save image file
        img_path = IMAGES_DIR / f"{student_id}.jpg"
        with open(img_path, "wb") as f:
            f.write(contents)

    return JSONResponse({"student_id": student_id, "status": "enrolled"})

@app.post("/match")
async def match(image: UploadFile = File(...)):
    contents = await image.read()
    try:
        input_img = face_recognition.load_image_file(contents)
    except Exception:
        from PIL import Image
        import io
        pil = Image.open(io.BytesIO(contents)).convert('RGB')
        input_img = np.array(pil)

    input_encs = face_recognition.face_encodings(input_img)
    if len(input_encs) == 0:
        raise HTTPException(status_code=400, detail="No face found in image")

    input_enc = input_encs[0]

    with lock:
        db = load_encodings()

    if not db:
        return JSONResponse({"matched": False, "reason": "no-enrollments"})

    best_id = None
    best_distance = 1.0
    for sid, enc_list in db.items():
        enc = np.array(enc_list)
        dist = face_recognition.face_distance([enc], input_enc)[0]
        if dist < best_distance:
            best_distance = dist
            best_id = sid

    # threshold (0.6 common); return score
    threshold = 0.6
    matched = best_id is not None and best_distance <= threshold

    return JSONResponse({"matched": matched, "student_id": best_id if matched else None, "distance": float(best_distance)})

@app.get("/students")
def students():
    with lock:
        db = load_encodings()
    return {"students": list(db.keys())}

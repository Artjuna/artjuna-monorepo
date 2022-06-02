from fastapi import FastAPI, UploadFile
from pydantic import BaseModel
from style_transfer_core import StyleTransferModel

app = FastAPI()
inference_model = StyleTransferModel()

class Image64(BaseModel):
    image_string:str

@app.get("/")
async def root():
    return {"root":"Success"}

@app.post("/inference")
async def inference(image_b64: Image64):
    image = inference_model.image_converter(image_b64.image_string)
    print(image.numpy().tolist())
    return {"result": 200 }

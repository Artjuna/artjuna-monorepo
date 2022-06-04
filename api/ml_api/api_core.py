import os

import mysql.connector
from dotenv import load_dotenv
from fastapi import FastAPI, Response
from pydantic import BaseModel

load_dotenv(os.path.join(".", ".env"))

from .style_transfer_core import StyleTransferModel

app = FastAPI()
inference_model = None
database = None


class ImagePair(BaseModel):
    image_style: str
    image_content: str


@app.on_event("startup")
async def startup_routine():
    global inference_model
    global database

    inference_model = StyleTransferModel()
    inference_model.load_model("adain_300")

    config = {
        "user": os.getenv("USERNAME"),
        "password": os.getenv("PASSWORD"),
        "host": os.getenv("HOST"),
    }

    database = mysql.connector.connect(**config)


@app.get("/")
async def root():
    return {"root": "Success"}


@app.post(
    "/styletransfer",
    responses={200: {"content": {"image/png": {}}}},
    response_class=Response,
)
async def style_transfer(
    image_pair: ImagePair,
):
    prepared_style = inference_model.image_converter(image_pair.image_style)
    prepared_content = inference_model.image_converter(image_pair.image_content)
    reconstructed_image = inference_model.style_transfer(
        prepared_style, prepared_content
    )

    return Response(content=reconstructed_image, media_type="image/png")

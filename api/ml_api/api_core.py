import os

import mysql.connector
from dotenv import load_dotenv
from fastapi import FastAPI, File, Form, Response, UploadFile

load_dotenv(os.path.join(".", ".env"))

from .style_transfer_core import StyleTransferModel

app = FastAPI()
style_transfer_model = None
database = None


@app.on_event("startup")
async def startup_routine():
    global style_transfer_model
    global database

    style_transfer_model = StyleTransferModel()
    style_transfer_model.load_model("adain_300")

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
async def style_transfer(ProductID: str = Form(), StyleImage: UploadFile = File()):
    cursor = database.cursor()

    # Get file URL from ProductID
    cursor.execute(
        "SELECT Image FROM Artjuna.produk WHERE ProductID = %(ProductID)s;",
        {"ProductID": ProductID},
    )
    file_url = cursor.fetchone()[0].strip("/")

    # Get file from local folder
    style_image = style_transfer_model.get_image(
        os.path.join("ProductImages", file_url)
    )
    content_image = style_transfer_model.get_image(StyleImage.file)

    prepared_style = style_transfer_model.image_preprocessing(style_image)
    prepared_content = style_transfer_model.image_preprocessing(content_image)
    reconstructed_image = style_transfer_model.style_transfer(
        prepared_style, prepared_content
    )

    return Response(content=reconstructed_image, media_type="image/png")

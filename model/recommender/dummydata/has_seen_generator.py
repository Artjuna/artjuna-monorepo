from dotenv import load_dotenv

load_dotenv()

import ast
import asyncio
import os
import random

import aiohttp
import mysql.connector
import requests as r
from tqdm import tqdm, trange


def get_data():
    config = ast.literal_eval(os.getenv("config"))
    db = mysql.connector.connect(**config)
    cursor = db.cursor()
    cursor.execute("SELECT UserID FROM Artjuna.account")
    data_user = cursor.fetchall()
    cursor.execute("SELECT ProductID FROM Artjuna.produk")
    data_product = cursor.fetchall()
    return data_user, data_product


async def main(dry_run=True, debug=True):
    base_url = os.getenv("base_url")
    data_user, data_product = get_data()

    async with aiohttp.ClientSession() as session:

        for i in trange(1000000):
            random_user = random.choice(data_user)
            random_product = random.choice(data_product)
            payload = {
                "UserID": random_user[0],
                "ProductID": random_product[0],
            }
            if debug:
                tqdm.write(str(payload))
            if not dry_run:
                async with session.post(
                    f"{base_url}/Product/Seen", json=payload
                ) as resp:
                    if debug:
                        tqdm.write(str(resp))


if __name__ == "__main__":
    dry_run = False
    debug = False
    asyncio.run(main(dry_run, debug))

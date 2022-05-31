from dotenv import load_dotenv

load_dotenv()

import mysql.connector
import os
import ast
import random
from tqdm import tqdm, trange
import requests as r
import aiohttp
import asyncio


def get_data():
    config = ast.literal_eval(os.getenv("config"))
    db = mysql.connector.connect(**config)
    cursor = db.cursor()
    cursor.execute("SELECT UserID FROM Artjuna.account")
    full_data = cursor.fetchall()
    return full_data


async def main(dry_run=True, debug=True):
    base_url = os.getenv("base_url")
    full_data = get_data()

    async with aiohttp.ClientSession() as session:

        for i in trange(10000):
            random_pair = random.sample(full_data, 2)
            payload = {
                "UserIDFollowing": random_pair[0][0],
                "UserIDFollowed": random_pair[1][0],
            }
            if debug:
                tqdm.write(str(payload))
            if not dry_run:
                async with session.post(
                    f"{base_url}/Follow/follow", json=payload
                ) as resp:
                    if debug:
                        tqdm.write(str(resp))


if __name__ == "__main__":
    dry_run = False
    debug = False
    asyncio.run(main(dry_run,debug))

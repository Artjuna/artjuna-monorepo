from dotenv import load_dotenv

load_dotenv()
import asyncio
import json
import os
import random

import aiohttp
import requests as r
from faker import Faker
from tqdm import tqdm, trange


def create_fake_num(fake):
    fake_telephone = fake.unique.phone_number()
    for character in "()- +":
        fake_telephone = fake_telephone.replace(character, "")
    if fake_telephone[0] == "0":
        fake_telephone = "62" + fake_telephone[1:]
    return fake_telephone


def create_fake_name(fake):
    fake_name = fake.unique.name()
    fake_name = fake_name.split(",")[0]
    fake_name = fake_name.split(". ")[-1]
    return fake_name


def create_random_city_province_pair(data_json):
    random_province_json = random.choice(data_json)
    random_province = random_province_json.split(".")[0]
    with open(f"location_data/{random_province_json}", "r") as f:
        raw_load = json.load(f)
        data_city_full = raw_load[random_province]
        random_city = random.choice(list(data_city_full.keys()))
    return random_province, random_city


async def main(dry_run=True, debug=True):
    base_url = os.getenv("base_url")
    fake = Faker("id_ID")
    data_json = os.listdir("location_data")

    async with aiohttp.ClientSession() as session:

        for i in trange(3000):

            # Name Gen
            fake_name = create_fake_name(fake)

            # Telephone Gen
            fake_telephone = create_fake_num(fake)

            # City Province Pair Gen
            random_province, random_city = create_random_city_province_pair(data_json)
            # Email Gen
            fake_email = fake.ascii_safe_email()

            # Username Gen
            fake_username = fake.user_name()

            # Store Randomizer
            is_store = random.choice([True, False])

            payload = {
                "Email": fake_email,
                "UserName": fake_username,
                "FullName": fake_name,
                "OriginProvince": random_province,
                "OriginCity": random_city,
                "Telephone": fake_telephone,
                "IsStore": is_store,
            }
            if debug:
                tqdm.write(str(payload))
            if not dry_run:
                async with session.post(
                    base_url + "/Account/addAccount", json=payload
                ) as resp:
                    response = resp
                    if debug:
                        tqdm.write(response)


if __name__ == "__main__":
    dry_run = False
    debug = False
    asyncio.run(main(dry_run, debug))

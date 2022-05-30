from dotenv import load_dotenv

load_dotenv()
import requests as r
import os
from faker import Faker
import json
import random


def create_fake_num(fake):
    fake_telephone = fake.unique.phone_number()
    for character in "()- +":
        fake_telephone = fake_telephone.replace(character, "")
    if fake_telephone[0] == "0":
        fake_telephone = "62"+fake_telephone[1:]
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


def main(dry_run=True):
    base_url = os.getenv("base_url")
    fake = Faker("id_ID")
    data_json = os.listdir("location_data")

    for i in range(1000):

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

        payload = {
            "Email": fake_email,
            "UserName": fake_username,
            "FullName": fake_name,
            "OriginProvince": random_province,
            "OriginCity": random_city,
            "Telephone": fake_telephone,
        }
        print(payload)
        if not dry_run:
            a = r.post(base_url + "/Account/addAccount", json=payload)
            print(a)


if __name__ == "__main__":
    main(False)

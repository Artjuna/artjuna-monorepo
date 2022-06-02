# Scraper part of the code made based on bing_image_downloader https://github.com/gurugaurav/bing_image_downloader
#
# This scraper is made tuned to the usage of this project
# because we don't need to save the image to our local storage


from dotenv import load_dotenv

load_dotenv()
import ast
import base64
import json
import os
import random
import re
import urllib
import urllib.request

import mysql.connector
import requests as r
from tqdm import trange


def create_random_city_province_pair(data_json):
    random_province_json = random.choice(data_json)
    random_province = random_province_json.split(".")[0]
    with open(f"location_data/{random_province_json}", "r") as f:
        raw_load = json.load(f)
        data_city_full = raw_load[random_province]
        random_city = random.choice(list(data_city_full.keys()))
    return random_province, random_city


def get_data():
    config = ast.literal_eval(os.getenv("config"))
    db = mysql.connector.connect(**config)
    cursor = db.cursor()
    cursor.execute("SELECT UserID FROM Artjuna.account WHERE IsStore=1;")
    full_data = cursor.fetchall()
    return full_data


def main(dry_run=True, debug=True):
    base_url = os.getenv("base_url")
    full_data = get_data()

    seen = set()

    headers = {
        "User-Agent": "Mozilla/5.0 (X11; Linux x86_64) "
        "AppleWebKit/537.11 (KHTML, like Gecko) "
        "Chrome/23.0.1271.64 Safari/537.11",
        "Accept": "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8",
        "Accept-Charset": "ISO-8859-1,utf-8;q=0.7,*;q=0.3",
        "Accept-Encoding": "none",
        "Accept-Language": "en-US,en;q=0.8",
        "Connection": "keep-alive",
    }

    category_list = ["batik", "ukiran kayu"]

    for i in trange(1):
        # Random UserID with IsStore=1
        random_userid = random.choice(full_data)[0]

        # Random City Province Pair Gen
        random_province, random_city = create_random_city_province_pair(
            os.listdir("location_data")
        )

        # Random Category Gen
        random_category = random.choice(category_list)
        random_image = scrape_bing_image(seen, headers, random_category)
        random_image_base64 = base64.b64encode(random_image).decode("utf-8")

        payload = {
            "UserID": random_userid,
            "ProductName": "HAAA",
            "Category": "HUH",
            "Province": random_province,
            "City": random_city,
            "Caption": "yukk bisa yukk",
            "Price": 15000,
            "Image": random_image_base64,
        }
        if debug:
            print(payload)
        if not dry_run:
            r.post(f"{base_url}/Product/addProduct", json=payload)

def scrape_bing_image(seen, headers, random_category):
    loop = True
    page_counter = 0
    while loop:
        request_url = (
                "https://www.bing.com/images/async?q="
                + urllib.parse.quote_plus(random_category)
                + "&first="
                + str(page_counter)
                + "&count=1000"
                + "&adlt="
                + str(False)
                + "&qft=+filterui:license-L2_L3_L4_L5_L6_L7"
            )
        request = urllib.request.Request(request_url, None, headers=headers)
        response = urllib.request.urlopen(request)
        html = response.read().decode("utf8")
        links = re.findall("murl&quot;:&quot;(.*?)&quot;", html)

        for link in links:
            if link not in seen:
                seen.add(link)
                request = urllib.request.Request(link, None, headers)
                image = urllib.request.urlopen(request, timeout=60).read()
                if image != None:
                    break
        if image != None:
            loop = False

    random_image = image
    return random_image


if __name__ == "__main__":
    dry_run = False
    debug = False
    main(dry_run, debug)

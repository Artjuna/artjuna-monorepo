import ast
import os

import mysql.connector
import pandas as pd
from dotenv import load_dotenv

load_dotenv()


def main(query, columns, name):
    full_data = get_data(query)
    # print(full_data)
    df = pd.DataFrame(full_data, columns=columns)
    df.to_parquet(name)


def get_data(query):
    config = ast.literal_eval(os.getenv("config"))
    db = mysql.connector.connect(**config)
    cursor = db.cursor()
    cursor.execute(query)
    full_data = cursor.fetchall()
    return full_data


def test_parquet():
    df = pd.read_parquet(
        "https://drive.google.com/uc?export=download&id=16kPOq9A0FKf6xfpmoguGnAwZjVoeh-kV"
    )
    print(df)


if __name__ == "__main__":
    # main("SELECT UserID FROM Artjuna.account;", ["UserID"], "dataset_user.parquet")
    # main(
    #     "SELECT ProductID FROM Artjuna.produk;",
    #     ["ProductID"],
    #     "dataset_product.parquet",
    # )
    main(
        "SELECT UserID,ProductID,createdAt FROM Artjuna.productSeen;",
        ["UserID", "ProductID", "createdAt"],
        "dataset_productSeen.parquet",
    )
